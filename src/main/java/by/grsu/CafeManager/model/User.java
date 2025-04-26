package by.grsu.CafeManager.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.Date;


@Component
@Scope("prototype")
@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private Date createdAt;
}
