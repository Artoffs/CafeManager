package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.DAO.interfaces.OrderDAO;
import by.grsu.CafeManager.DAO.interfaces.TableDAO;
import by.grsu.CafeManager.model.OrderForm;
import by.grsu.CafeManager.service.impl.DishServiceImpl;
import by.grsu.CafeManager.service.impl.OrderDetailsServiceImpl;
import by.grsu.CafeManager.service.interfaces.OrderService;
import by.grsu.CafeManager.service.interfaces.TableService;
import by.grsu.CafeManager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    private final OrderService orderService;
    private final TableService tableService;
    private final DishServiceImpl dishService;
    private final UserService userService;
    private final OrderDetailsServiceImpl orderDetailsService;

    @Autowired
    public OrderController(DishServiceImpl dishService, TableService tableService, OrderService orderService, UserService userService,
                           OrderDetailsServiceImpl orderDetailsService) {
        this.dishService = dishService;
        this.tableService = tableService;
        this.orderService = orderService;
        this.userService = userService;
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/orders")
    public String allOrders(Model model, Principal principal) {
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("current_user", principal.getName());
        return "orders";
    }

    @GetMapping("/orders/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
    public String showOrderForm(Model model) {
        model.addAttribute("dishes", dishService.getAllCurrent()); // Список всех блюд
        model.addAttribute("orderForm", new OrderForm()); // Пустая форма заказа
        model.addAttribute("tables", tableService.getAll()); // Пустая форма заказа
        return "order_form";
    }

    @PostMapping("/orders/create")
    @Transactional
    public String submitOrder(@ModelAttribute OrderForm orderForm,
                              BindingResult result,
                              Model model,
                              Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("dishes", dishService.getAll());
            return "order_form";
        }

        orderForm.setUserId(userService.getUserByUsername(principal.getName()).getId());
        // Обработка заказа
        orderService.saveOrder(orderForm);
        orderDetailsService.saveOrderItems(orderForm);
        return "redirect:/orders";
    }

    @DeleteMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
