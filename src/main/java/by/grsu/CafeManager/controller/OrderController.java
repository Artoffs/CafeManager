package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.model.Order;
import by.grsu.CafeManager.model.OrderForm;
import by.grsu.CafeManager.model.OrderItem;
import by.grsu.CafeManager.model.User;
import by.grsu.CafeManager.model.enums.OrderStatus;
import by.grsu.CafeManager.service.DTO.UserDTO;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("dishes", dishService.getAllCurrent());
        model.addAttribute("orderForm", new OrderForm());
        model.addAttribute("tables", tableService.getAll());
        return "order_form";
    }

    @PostMapping("/orders/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderWithItems(id);
        model.addAttribute("order", order);


        double total = 0;
        if (order.getItems() != null) {
            total = order.getItems().stream()
                    .mapToDouble(item -> item.getDish().getPrice() * item.getQuantity())
                    .sum();
        }
        model.addAttribute("totalAmount", total);
        model.addAttribute("statuses", OrderStatus.values());

        return "order_details";
    }

    @GetMapping("/orders/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
    public String showEditOrderForm(@PathVariable Long id, Model model) {

        Order order = orderService.getOrderWithItems(id);

        OrderForm orderForm = convertToOrderForm(order);

        model.addAttribute("orderForm", orderForm);
        model.addAttribute("order", order);
        model.addAttribute("orderId", id);
        model.addAttribute("dishes", dishService.getAllCurrent());
        model.addAttribute("tables", tableService.getAll());

        return "order_edit_form";
    }

    @PostMapping("/orders/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER')")
    @Transactional
    public String updateOrder(@PathVariable Long id,
                              @ModelAttribute OrderForm orderForm,
                              BindingResult result,
                              Model model,
                              Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("dishes", dishService.getAllCurrent());
            model.addAttribute("tables", tableService.getAll());
            return "order_edit_form";
        }

        try {
            // Обновляем заказ
            orderForm.setOrderId(id);
            orderService.updateFullOrder(orderForm, principal.getName());
            return "redirect:/orders/" + id;
        } catch (IllegalStateException | IllegalArgumentException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            model.addAttribute("dishes", dishService.getAllCurrent());
            model.addAttribute("tables", tableService.getAll());
            return "order_edit_form";
        }
    }

    @PostMapping("/orders/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'WAITER', 'COOK')")
    public String changeOrderStatus(@PathVariable Long id,
                                    @RequestParam(name = "status") String statusString,
                                    Principal principal) {
        try {
            // Конвертируем строку в enum
            OrderStatus status = OrderStatus.valueOf(statusString);

            // Получаем текущего пользователя и его роль
            UserDTO currentUser = userService.getUserByUsername(principal.getName());

            orderService.changeOrderStatus(id, status, currentUser.getRole());
            return "redirect:/orders/" + id;
        } catch (IllegalStateException | IllegalArgumentException e) {
            return "redirect:/orders/" + id + "?error=" + e.getMessage();
        }
    }


    private OrderForm convertToOrderForm(Order order) {
        OrderForm form = new OrderForm();
        form.setOrderId(order.getId());
        form.setTableId(order.getTable().getId());


        List<OrderForm.OrderItemDto> items = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderForm.OrderItemDto dto = new OrderForm.OrderItemDto();
            dto.setDishId(item.getDish().getId());
            dto.setQuantity(item.getQuantity());
            dto.setComment(item.getComment());
            items.add(dto);
        }
        form.setItems(items);

        return form;
    }
}
