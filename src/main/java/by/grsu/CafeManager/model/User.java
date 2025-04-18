package by.grsu.CafeManager.model;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;


@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createdAt;

}
