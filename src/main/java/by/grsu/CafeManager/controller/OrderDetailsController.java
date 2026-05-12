package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.service.impl.OrderDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderDetailsController {

    private final OrderDetailsServiceImpl orderDetailsService;

    @Autowired
    public OrderDetailsController(OrderDetailsServiceImpl orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

//    @GetMapping("/orders/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
//    public String orderDetails(@PathVariable String id, Model model) {
//        model.addAttribute("orderDetails", orderDetailsService.getByOrderId(Long.parseLong(id)));
//        return "order_details";
//    }
}
