package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    Optional<Order> getOrder(Long id);
    List<Order> getOrders();
    Order saveOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(Long id);
}
