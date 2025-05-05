package by.grsu.CafeManager.service.interfaces;

import by.grsu.CafeManager.DAO.interfaces.UserDAO;
import by.grsu.CafeManager.model.User;
import by.grsu.CafeManager.model.enums.Role;
import by.grsu.CafeManager.service.DTO.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUser(Long id);
    List<UserDTO> getAll();
    void updateUser(UserDTO user);
    UserDTO saveUser(UserDTO user);
    void deleteUser(UserDTO user);

}
