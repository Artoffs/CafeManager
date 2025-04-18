package by.grsu.CafeManager.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private Long id;
    private User user;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private float totalPrice;
}
