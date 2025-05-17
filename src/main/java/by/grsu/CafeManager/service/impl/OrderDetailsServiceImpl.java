package by.grsu.CafeManager.service.impl;

import by.grsu.CafeManager.DAO.interfaces.OrderItemDAO;
import by.grsu.CafeManager.model.OrderForm;
import by.grsu.CafeManager.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void saveOrderItems(OrderForm orderForm) {
        for (OrderForm.OrderItemDto item : orderForm.getItems()) {
            item.setOrderId(orderForm.getOrderId());
            orderItemDAO.saveOrderItem(item);
        }
    }
}

