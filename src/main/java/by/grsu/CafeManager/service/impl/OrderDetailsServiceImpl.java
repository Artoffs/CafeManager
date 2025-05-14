package by.grsu.CafeManager.service.impl;

import by.grsu.CafeManager.DAO.interfaces.OrderItemDAO;
import by.grsu.CafeManager.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl {

    private final OrderItemDAO orderItemDAO;

    @Autowired
    public OrderDetailsServiceImpl(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    public List<OrderItem> getByOrderId(Long id) {
        return orderItemDAO.getOrderItemsByOrderId(id);
    }
}

