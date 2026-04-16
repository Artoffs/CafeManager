package by.grsu.CafeManager.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderForm {
    private Long orderId;
    private List<OrderItemDto> items = new ArrayList<>();
    private Long tableId;
    private Long userId;

    @Data
    public static class OrderItemDto {
        private Long orderId;
        private Long dishId;
        private int quantity;
        private String comment;
    }
}
