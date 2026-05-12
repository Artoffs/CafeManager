package by.grsu.CafeManager.service.impl;

import by.grsu.CafeManager.DAO.interfaces.OrderDAO;
import by.grsu.CafeManager.DAO.interfaces.OrderItemDAO;
import by.grsu.CafeManager.model.*;
import by.grsu.CafeManager.model.enums.OrderStatus;
import by.grsu.CafeManager.model.enums.Role;
import by.grsu.CafeManager.service.DTO.UserDTO;
import by.grsu.CafeManager.service.interfaces.OrderService;
import by.grsu.CafeManager.service.interfaces.TableService;
import by.grsu.CafeManager.service.interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final OrderDetailsServiceImpl orderDetailsService;
    private final UserService userService;
    private final TableService tableService;

    public OrderServiceImpl(OrderDAO orderDAO, OrderItemDAO orderItemDAO, OrderDetailsServiceImpl orderDetailsService, UserService userService, TableService tableService) {
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.orderDetailsService = orderDetailsService;
        this.userService = userService;
        this.tableService = tableService;
    }

    @Override
    public Order getOrder(Long id) {
        return orderDAO.getOrder(id).orElseThrow(()
                -> new IllegalArgumentException("Заказ с таким id не найден"));
    }

    @Override
    public List<Order> getAll() {
        return orderDAO.getOrders();
    }

    @Override
    @Transactional
    public void updateOrder(Order order) {
        if (order == null || order.getId() == null) {
            throw new IllegalArgumentException("Заказ или его ID не могут быть null");
        }

        Order existingOrder = getOrder(order.getId());

        // нельзя редактировать завершенные или отмененные заказы
        if (existingOrder.getOrderStatus() == OrderStatus.COMPLETED ||
                existingOrder.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    String.format("Нельзя редактировать заказ со статусом '%s'",
                            existingOrder.getOrderStatus())
            );
        }


        if (order.getTable() == null || order.getTable().getId() == null) {
            throw new IllegalArgumentException("Столик должен быть указан");
        }
        if (order.getUser() == null || order.getUser().getId() == null) {
            throw new IllegalArgumentException("Пользователь (официант) должен быть указан");
        }


        orderDAO.updateOrder(order);
    }

    @Override
    @Transactional
    public void saveOrder(OrderForm orderForm) {

        if (orderForm.getTableId() == null) {
            throw new IllegalArgumentException("Необходимо выбрать столик");
        }
        if (orderForm.getUserId() == null) {
            throw new IllegalArgumentException("Необходимо указать официанта");
        }

        orderDAO.saveOrder(orderForm);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {

        Order order = getOrder(id);
        if (order.getOrderStatus() == OrderStatus.IN_PROGRESS ||
                order.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException(
                    String.format("Нельзя удалить заказ со статусом '%s'", order.getOrderStatus())
            );
        }

        orderDAO.deleteOrder(id);
    }


    @Transactional
    public void changeOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrder(orderId);


        order.setOrderStatus(newStatus);
        orderDAO.updateOrder(order);
    }

    public Order getOrderWithItems(Long id) {
        Order order = getOrder(id);

        List<OrderItem> items = orderDetailsService.getByOrderId(id);
        order.setItems(items);

        return order;
    }

    @Override
    @Transactional
    public void updateFullOrder(OrderForm orderForm, String username) {

        Order existingOrder = getOrder(orderForm.getOrderId());

        if (existingOrder.getOrderStatus() == OrderStatus.COMPLETED ||
                existingOrder.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Нельзя редактировать завершенный или отмененный заказ");
        }

        UserDTO userByUsername = userService.getUserByUsername(username);
        User user = User.builder()
                .id(userByUsername.getId())
                .username(userByUsername.getName())
                .role(userByUsername.getRole())
                .build();
        Table table = tableService.getTable(orderForm.getTableId());

        Order updatedOrder = Order.builder()
                .id(existingOrder.getId())
                .table(table)
                .user(user)
                .orderStatus(existingOrder.getOrderStatus())
                .createdAt(existingOrder.getCreatedAt())
                .build();

        orderDAO.updateOrder(updatedOrder);


        updateOrderItems(orderForm);
    }

    @Transactional
    @Override
    public void changeOrderStatus(Long orderId, OrderStatus newStatus, Role currentUserRole) {

        Order order = getOrder(orderId);
        OrderStatus currentStatus = order.getOrderStatus();

        validateStatusChangePermissions(currentStatus, newStatus, currentUserRole);

        order.setOrderStatus(newStatus);
        orderDAO.updateOrder(order);
    }

    private void validateStatusChangePermissions(OrderStatus currentStatus,
                                                 OrderStatus newStatus,
                                                 Role userRole) {
        switch (userRole) {
            case WAITER:
                if (newStatus == OrderStatus.IN_PROGRESS || newStatus == OrderStatus.READY) {
                    throw new IllegalStateException("Официант не может перевести заказ в статус " + newStatus);
                }
                if (currentStatus == OrderStatus.IN_PROGRESS && newStatus == OrderStatus.CANCELLED) {
                    throw new IllegalStateException("Официант не может отменить заказ, который уже готовится");
                }
                break;

            case COOK:
                // Повар может только переводить в READY и видеть заказы
                if (newStatus != OrderStatus.READY) {
                    throw new IllegalStateException("Повар может только отметить заказ как готовый (READY)");
                }
                if (currentStatus != OrderStatus.IN_PROGRESS) {
                    throw new IllegalStateException("Повар может готовить только заказы со статусом IN_PROGRESS");
                }
                break;

            case ADMIN:

                break;

            default:
                throw new IllegalStateException("Недостаточно прав для изменения статуса заказа");
        }
    }


    private void updateOrderItems(OrderForm orderForm) {
        List<OrderItem> currentItems = orderDetailsService.getByOrderId(orderForm.getOrderId());


        List<OrderForm.OrderItemDto> newItems = orderForm.getItems();

        for (OrderItem item : currentItems) {
            orderItemDAO.deleteOrderItem(item);
        }


        for (OrderForm.OrderItemDto item : newItems) {
            if (item.getDishId() != null && item.getQuantity() > 0) {
                item.setOrderId(orderForm.getOrderId());
                orderItemDAO.saveOrderItem(item);
            }
        }
    }
}