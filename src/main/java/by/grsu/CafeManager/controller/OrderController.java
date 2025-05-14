package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.DAO.interfaces.OrderDAO;
import by.grsu.CafeManager.DAO.interfaces.TableDAO;
import by.grsu.CafeManager.model.OrderForm;
import by.grsu.CafeManager.service.impl.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class OrderController {

    private final OrderDAO orderDAO;
    private TableDAO tableDAO;
    private final DishServiceImpl dishService;

    @Autowired
    public OrderController(DishServiceImpl dishService, TableDAO tableDAO, OrderDAO orderDAO) {
        this.dishService = dishService;
        this.tableDAO = tableDAO;
        this.orderDAO = orderDAO;
    }

    @GetMapping("/orders")
    public String allOrders(Model model, Principal principal) {
        model.addAttribute("orders", orderDAO.getOrders());
        model.addAttribute("tables", tableDAO.getTables());
        model.addAttribute("current_user", principal.getName());
        return "orders";
    }

    @GetMapping("/order/create")
    public String showOrderForm(Model model) {
        model.addAttribute("dishes", dishService.getAll()); // Список всех блюд
        model.addAttribute("orderForm", new OrderForm()); // Пустая форма заказа
        return "order_form";
    }

    @PostMapping("/order/create")
    public String submitOrder(@ModelAttribute OrderForm orderForm,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("dishes", dishService.getAll());
            return "order_form";
        }

        // Обработка заказа
//        orderService.createOrder(orderForm);
        return "redirect:/orders";
    }

    @DeleteMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderDAO.deleteOrder(id);
        return "redirect:/orders";
    }
}
