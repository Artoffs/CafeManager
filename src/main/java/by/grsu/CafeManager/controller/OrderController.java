package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.DAO.interfaces.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class OrderController {

    private OrderDAO orderDAO;

    @Autowired
    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping("/orders")
    public String allOrders(Model model, Principal principal) {
        model.addAttribute("orders", orderDAO.getOrders());
        model.addAttribute("current_user", principal.getName());
        return "orders";
    }
}
