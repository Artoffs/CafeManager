package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.IOrderDAO;
import by.grsu.CafeManager.model.Order;
import by.grsu.CafeManager.model.OrderStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderDAOPostgresImpl implements IOrderDAO {

    private static DataSource dataSource;

    public static final String GET = "SELECT id, user_id, order_status, createdat, totalprice FROM public.\"order\" WHERE id=?;";
    public static final String GET_ALL = "SELECT id, user_id, order_status, createdat, totalprice FROM public.\"order\";";
    public static final String INSERT = "INSERT INTO public.\"order\"(user_id, order_status, createdat, totalprice) VALUES (?, ?, ?, ?);";
    public static final String UPDATE = "UPDATE public.\"order\" SET id=?, user_id=?, order_status=?, createdat=?, totalprice=? WHERE <condition>;";
    public static final String DELETE = "DELETE FROM public.\"order\" WHERE id=?;";



    // TODO юзер
    @Override
    public Order getOrder(Long id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET);
            preparedStatement.setLong(0, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return Order.builder()
                        .id(resultSet.getLong("id"))
                        .orderStatus(OrderStatus.valueOf(resultSet.getString("order_status")))
                        .createdAt(resultSet.getDate("createdAt"))
                        .totalPrice(resultSet.getFloat("totalPrice"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Order order = Order.builder()
                        .id(resultSet.getLong("id"))
                        .orderStatus(OrderStatus.valueOf(resultSet.getString("order_status")))
                        .createdAt(resultSet.getDate("createdAt"))
                        .totalPrice(resultSet.getFloat("totalPrice"))
                        .build();
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    @Override
    public Order saveOrder(Order order) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setLong(0, order.getUser().getId());
            preparedStatement.setString(1, order.getOrderStatus().name());
            preparedStatement.setDate(2, (Date) order.getCreatedAt());
            preparedStatement.setFloat(3, order.getTotalPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public boolean updateOrder(Order order) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setLong(0, order.getId());
            preparedStatement.setLong(1, order.getUser().getId());
            preparedStatement.setString(2, order.getOrderStatus().name());
            preparedStatement.setDate(3, (Date) order.getCreatedAt());
            preparedStatement.setFloat(4, order.getTotalPrice());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteOrder(Order order) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(0, order.getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
