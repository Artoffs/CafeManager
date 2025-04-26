package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.IUserDAO;
import by.grsu.CafeManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component("dao")
public class UserDAOPostgresImpl implements IUserDAO {

    private final DataSource dataSource;

    // sql.properties
    @Value("${user.get}")
    private String GET;
    @Value("${user.getAll}")
    private String SELECT_ALL;
    @Value("${user.insert}")
    private String INSERT;
    @Value("${user.update}")
    private String UPDATE;
    @Value("${user.delete}")
    private String DELETE;

    @Autowired
    public UserDAOPostgresImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> getUser(Long id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next()
                    ? Optional.of(mapRowToUser(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                users.add(mapRowToUser(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User saveUser(User user) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            preparedStatement.setString(0, user.getUsername());
//            preparedStatement.setString(1, user.getUsername()); хеш пароля здесь надо
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setLong(0, user.getId());
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setDate(3, (Date) user.getCreatedAt());
            preparedStatement.setLong(4, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean deleteUser(User user) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(0, user.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User mapRowToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .build();
    }
}
