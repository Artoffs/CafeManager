package by.grsu.CafeManager.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {
    private Long id;
    private Order order;
    private Dish dish;
    private int quantity;
    private String comment;
}
