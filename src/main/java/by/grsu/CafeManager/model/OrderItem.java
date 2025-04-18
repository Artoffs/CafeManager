package by.grsu.CafeManager.model;

import lombok.Data;

@Data
public class OrderItem {
    private Long id;
    private Order order;
    private User user;
    private int quantity;
}
