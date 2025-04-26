package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.IOrderItemDAO;
import by.grsu.CafeManager.model.OrderItem;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrderItemDAOPostgresImpl implements IOrderItemDAO {

    private static DataSource dataSource;

    @Override
    public OrderItem getOrderItem(Long id) {
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
    public List<OrderItem> getOrderItems() {
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
    public OrderItem saveOrderItem(OrderItem orderItem) {
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
    public OrderItem updateOrderItem(OrderItem orderItem) {
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
    public void deleteOrderItem(OrderItem orderItem) {
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
