package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    Optional<User> getUser(Long id);
    List<User> getUsers();
    User saveUser(User user);
    User updateUser(User user);
    boolean deleteUser(User user);
}
