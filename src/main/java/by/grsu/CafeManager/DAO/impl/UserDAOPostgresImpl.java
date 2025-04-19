package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.IUserDAO;
import by.grsu.CafeManager.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOPostgresImpl implements IUserDAO {

    private static DataSource dataSource;

    private static final String GET = "SELECT id, username, password, createdAt FROM public.\"user\" WHERE id=?;";
    private static final String SELECT_ALL = "SELECT id, username, password, createdAt FROM public.\"user\";";
    private static final String INSERT = "INSERT INTO public.\"user\"(\n\tid, username, password, \"createdAt\")\n\tVALUES (?, ?, ?, ?);";
    public static final String UPDATE = "UPDATE public.\"user\"SET id=?, username=?, password=?, \"createdAt\"=? WHERE id=?;";
    public static final String DELETE = "DELETE FROM public.\"user\" WHERE id=?;";

    @Override
    public User getUser(Long id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET);
            preparedStatement.setLong(0, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return User.builder()
                        .id(resultSet.getLong("username"))
                        .password(resultSet.getString("password"))
                        .createdAt(resultSet.getDate("createdAt"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("username"))
                        .password(resultSet.getString("password"))
                        .createdAt(resultSet.getDate("createdAt"))
                        .build();
               users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User saveUser(User user) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(0, user.getUsername());
//            preparedStatement.setString(1, user.getUsername()); хеш пароля здесь надо
            boolean execute = preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setLong(0, user.getId());
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setDate(3, (Date) user.getCreatedAt());
            preparedStatement.setLong(4, user.getId());
            boolean execute = preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void deleteUser(User user) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(0, user.getId());
            boolean execute = preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
