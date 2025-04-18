package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.Order;

import java.util.List;

public interface IOrderDAO {
    Order getOrder(Long id);
    List<Order> getOrders();
    Order saveOrder(Order order);
    Order updateOrder(Order order);
    void deleteOrder(Order order);
}
