package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.model.Order;
import by.grsu.CafeManager.model.User;
import by.grsu.CafeManager.model.enums.OrderStatus;
import by.grsu.CafeManager.service.DTO.UserDTO;
import by.grsu.CafeManager.service.impl.OrderDetailsServiceImpl;
import by.grsu.CafeManager.service.interfaces.OrderService;
import by.grsu.CafeManager.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final OrderService orderService;
    private final UserService userService;
    private final OrderDetailsServiceImpl orderDetailsService;

    public MainController(OrderService orderService, UserService userService, OrderDetailsServiceImpl orderDetailsService) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        String username = principal.getName();
        UserDTO currentUser = userService.getUserByUsername(username);
        model.addAttribute("current_user", username);
        model.addAttribute("user_role", currentUser.getRole());

        switch (currentUser.getRole()) {
            case ADMIN:
                prepareAdminDashboard(model);
                break;
            case WAITER:
                prepareWaiterDashboard(model, currentUser);
                break;
            case COOK:
                prepareCookDashboard(model);
                break;
        }

        return "home";
    }

    private void prepareAdminDashboard(Model model) {
        // Общая статистика
        List<Order> allOrders = orderService.getAll();
        long totalOrders = allOrders.size();
        double totalRevenue = calculateTotalRevenue(allOrders);
        long activeOrdersCount = allOrders.stream()
                .filter(o -> o.getOrderStatus() != OrderStatus.COMPLETED &&
                        o.getOrderStatus() != OrderStatus.CANCELLED)
                .count();

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("activeOrdersCount", activeOrdersCount);

        // Заказы по статусам
        Map<OrderStatus, Long> ordersByStatus = allOrders.stream()
                .collect(Collectors.groupingBy(Order::getOrderStatus, Collectors.counting()));
        model.addAttribute("ordersByStatus", ordersByStatus);

        // Последние 5 заказов
        List<Order> recentOrders = allOrders.stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("recentOrders", recentOrders);

        // Статистика по официантам
        Map<String, Long> ordersByWaiter = allOrders.stream()
                .collect(Collectors.groupingBy(o -> o.getUser().getUsername(), Collectors.counting()));
        model.addAttribute("ordersByWaiter", ordersByWaiter);

        // За сегодня статистика
        Date today = new Date();
        long ordersToday = allOrders.stream()
                .filter(o -> isSameDay(o.getCreatedAt(), today))
                .count();
        model.addAttribute("ordersToday", ordersToday);
    }

    private void prepareWaiterDashboard(Model model, UserDTO waiter) {
        // Активные заказы (не завершенные и не отмененные)
        List<Order> activeOrders = orderService.getAll().stream()
                .filter(o -> o.getUser().getId().equals(waiter.getId()))
                .filter(o -> o.getOrderStatus() != OrderStatus.COMPLETED &&
                        o.getOrderStatus() != OrderStatus.CANCELLED)
                .collect(Collectors.toList());
        model.addAttribute("activeOrders", activeOrders);

        // Завершенные заказы сегодня
        Date today = new Date();
        long completedToday = orderService.getAll().stream()
                .filter(o -> o.getUser().getId().equals(waiter.getId()))
                .filter(o -> o.getOrderStatus() == OrderStatus.COMPLETED)
                .filter(o -> isSameDay(o.getCreatedAt(), today))
                .count();
        model.addAttribute("completedToday", completedToday);

        // Итого за сегодня
        double revenueToday = orderService.getAll().stream()
                .filter(o -> o.getUser().getId().equals(waiter.getId()))
                .filter(o -> o.getOrderStatus() == OrderStatus.COMPLETED)
                .filter(o -> isSameDay(o.getCreatedAt(), today))
                .mapToDouble(this::calculateOrderTotal)
                .sum();
        model.addAttribute("revenueToday", revenueToday);

        // Статистика по активным заказам
        Map<OrderStatus, Long> activeStatuses = activeOrders.stream()
                .collect(Collectors.groupingBy(Order::getOrderStatus, Collectors.counting()));
        model.addAttribute("activeStatuses", activeStatuses);
    }

    private void prepareCookDashboard(Model model) {
        // Заказы в работе
        List<Order> inProgressOrders = orderService.getAll().stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.IN_PROGRESS)
                .collect(Collectors.toList());
        model.addAttribute("inProgressOrders", inProgressOrders);

        // Заказы готовые к выдаче
        List<Order> readyOrders = orderService.getAll().stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.READY)
                .collect(Collectors.toList());
        model.addAttribute("readyOrders", readyOrders);

        // Ожидающие заказы (созданные)
        List<Order> pendingOrders = orderService.getAll().stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.CREATED)
                .collect(Collectors.toList());
        model.addAttribute("pendingOrders", pendingOrders);

        // Всего заказов в работе
        model.addAttribute("totalInKitchen", inProgressOrders.size() + readyOrders.size());
    }

    private double calculateTotalRevenue(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.COMPLETED)
                .mapToDouble(this::calculateOrderTotal)
                .sum();
    }

    private double calculateOrderTotal(Order order) {
        // Нужно реализовать расчет суммы заказа
        // Можно через сервис или прямую выборку
        return orderDetailsService.getByOrderId(order.getId()).stream()
                .mapToDouble(item -> item.getDish().getPrice() * item.getQuantity())
                .sum();
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}