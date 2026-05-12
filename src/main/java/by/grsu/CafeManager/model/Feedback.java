package by.grsu.CafeManager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private Long id;
    private Long orderId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}