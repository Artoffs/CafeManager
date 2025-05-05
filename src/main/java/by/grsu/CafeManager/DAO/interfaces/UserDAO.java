package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> getUser(Long id);
    List<User> getUsers();
    User saveUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
