package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.OrderForm;
import by.grsu.CafeManager.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemDAO {
    Optional<OrderItem> getOrderItem(Long id);
    List<OrderItem> getOrderItems();
    void saveOrderItem(OrderForm.OrderItemDto orderItem);
    void updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(OrderItem orderItem);
    public List<OrderItem> getOrderItemsByOrderId(Long id);
}
