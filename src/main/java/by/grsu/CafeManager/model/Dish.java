package by.grsu.CafeManager.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dish {
    private Long id;
    private String name;
    private String description;
    private float price;
    private String category;
    private boolean isAvailable;
    private boolean isDeleted;
}
