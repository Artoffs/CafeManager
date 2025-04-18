package by.grsu.CafeManager.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dish {
    private Long id;
    private String name;
    private float price;
    private String description;
    private boolean isAvailable;
}
