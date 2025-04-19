package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.Order;

import java.util.List;

public interface IOrderDAO {
    Order getOrder(Long id);
    List<Order> getOrders();
    Order saveOrder(Order order);
    boolean updateOrder(Order order);
    boolean deleteOrder(Order order);
}
