package by.grsu.CafeManager.service.interfaces;

import by.grsu.CafeManager.model.Order;
import by.grsu.CafeManager.model.OrderForm;
import by.grsu.CafeManager.model.enums.OrderStatus;
import by.grsu.CafeManager.model.enums.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {
    Order getOrder(Long id);
    List<Order> getAll();
    void updateOrder(Order order);
    void saveOrder(OrderForm order);
    Order getOrderWithItems(Long id);
    void deleteOrder(Long id);
    void updateFullOrder(OrderForm orderForm, String username);
    void changeOrderStatus(Long orderId, OrderStatus newStatus, Role currentUserRole);

}
