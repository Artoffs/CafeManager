package by.grsu.CafeManager.service.DTO;

import by.grsu.CafeManager.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 15)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 8, max = 15)
    private String password;
    @NotNull
    private Role role;

}
