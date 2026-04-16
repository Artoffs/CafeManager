package by.grsu.CafeManager.service.interfaces;
import by.grsu.CafeManager.service.DTO.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUser(Long id);
    UserDTO getUserByUsername(String username);
    List<UserDTO> getAll();
    UserDTO saveUser(UserDTO user);
}
