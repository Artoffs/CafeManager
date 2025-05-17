package by.grsu.CafeManager.service.impl;

import by.grsu.CafeManager.DAO.interfaces.OrderDAO;
import by.grsu.CafeManager.model.Order;
import by.grsu.CafeManager.model.OrderForm;
import by.grsu.CafeManager.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public Order getOrder(Long id) {
        return orderDAO.getOrder(id).orElseThrow(() -> new IllegalArgumentException("Заказ с таким id не найден"));
    }

    @Override
    public List<Order> getAll() {
        return orderDAO.getOrders();
    }

    @Override
    public void updateOrder(Order order) {

    }

    @Override
    public void saveOrder(OrderForm orderForm) {
        orderDAO.saveOrder(orderForm);
    }

    @Override
    public void deleteOrder(Long id) {
        orderDAO.deleteOrder(id);
    }
}
