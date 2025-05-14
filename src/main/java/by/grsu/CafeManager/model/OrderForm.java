package by.grsu.CafeManager.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderForm {
    private List<OrderItemDto> items = new ArrayList<>();

    @Data
    public static class OrderItemDto {
        private Long dishId;
        private Long quantity;
    }
}
