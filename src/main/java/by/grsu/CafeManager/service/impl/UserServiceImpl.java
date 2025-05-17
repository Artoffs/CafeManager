package by.grsu.CafeManager.service.impl;

import by.grsu.CafeManager.DAO.interfaces.UserDAO;
import by.grsu.CafeManager.model.User;
import by.grsu.CafeManager.service.DTO.UserDTO;
import by.grsu.CafeManager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userDAO.getUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким id не найден, id: " + id));
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userDAO.getByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким username не найден"));
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    @Override
    public List<UserDTO> getAll() {
        return userDAO.getUsers().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole()))
                .toList();
    }

    @Override
    public void updateUser(UserDTO user) {

    }

    @Override
    public void deleteUser(UserDTO user) {

    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        if(userDTO.getName() == null || userDTO.getName().isBlank()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        } else if (userDAO.getByUsername(userDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        User user = new User();
        user.setUsername(userDTO.getName());
        user.setRole(userDTO.getRole());
        user.setPassword(userDTO.getPassword());

        User savedUser = userDAO.saveUser(user);

        return new UserDTO(savedUser.getId(), savedUser.getUsername(),savedUser.getPassword(), savedUser.getRole());
    }

}