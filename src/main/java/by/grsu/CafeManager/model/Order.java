package by.grsu.CafeManager.model;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class Order {
    private Long id;
    private User user;
    private OrderStatus orderStatus;
    private Date createdAt;
    private float totalPrice;
}
