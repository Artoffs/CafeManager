package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.OrderItem;

import java.util.List;

public interface IOrderItemDAO {
    OrderItem getOrderItem(Long id);
    List<OrderItem> getOrderItems();
    OrderItem saveOrderItem(OrderItem orderItem);
    OrderItem updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(OrderItem orderItem);
}
