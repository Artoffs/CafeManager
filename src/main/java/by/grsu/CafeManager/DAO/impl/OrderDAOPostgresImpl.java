package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.IOrderDAO;
import by.grsu.CafeManager.model.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOPostgresImpl implements IOrderDAO {

    private static DataSource dataSource;

    @Override
    public Order getOrder(Long id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
//                return User.builder().id(resultSet.getLong(0))...
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Order> getOrders() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
//                return User.builder().id(resultSet.getLong(0))...
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Order saveOrder(Order order) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
//                return User.builder().id(resultSet.getLong(0))...
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Order updateOrder(Order order) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("");
            boolean execute = preparedStatement.execute();
            if(execute) {
//                return User.builder().id(resultSet.getLong(0))...
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void deleteOrder(Order order) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
//                return User.builder().id(resultSet.getLong(0))...
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
