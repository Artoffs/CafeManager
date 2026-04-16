package by.grsu.CafeManager.service.interfaces;

import by.grsu.CafeManager.model.Order;
import by.grsu.CafeManager.model.OrderForm;

import java.util.List;

public interface OrderService {
    Order getOrder(Long id);
    List<Order> getAll();
    void updateOrder(Order order);
    void saveOrder(OrderForm order);

    void deleteOrder(Long id);
}
