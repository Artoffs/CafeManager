package by.grsu.CafeManager.model;

import lombok.Builder;
import lombok.Data;


import java.util.Date;


@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private Date createdAt;

}
