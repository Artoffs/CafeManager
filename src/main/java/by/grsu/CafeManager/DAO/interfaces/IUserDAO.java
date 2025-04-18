package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.User;

import java.util.List;

public interface IUserDAO {
    User getUser(Long id);
    List<User> getUsers();
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(User user);
}
