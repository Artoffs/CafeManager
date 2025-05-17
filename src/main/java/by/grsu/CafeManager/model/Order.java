package by.grsu.CafeManager.model;

import by.grsu.CafeManager.model.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class Order {
    private Long id;
    private Table table;
    private User user;
    private OrderStatus orderStatus;
    private Date createdAt;

}
