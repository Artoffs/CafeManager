package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderDAO {
    Optional<Order> getOrder(Long id);
    List<Order> getOrders();
    Order saveOrder(Order order);
    Order updateOrder(Order order);
    boolean deleteOrder(Order order);
}
