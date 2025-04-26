package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.IOrderDAO;
import by.grsu.CafeManager.model.Order;
import by.grsu.CafeManager.model.OrderStatus;
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

@Component
public class OrderDAOPostgresImpl implements IOrderDAO {

    private final DataSource dataSource;

    @Value("${order.get}")
    private String GET;
    @Value("${order.getAll}")
    private String GET_ALL;
    @Value("${order.insert}")
    private String INSERT;
    @Value("${order.update}")
    private String UPDATE;
    @Value("${order.delete}")
    private String DELETE;

    @Autowired
    public OrderDAOPostgresImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // TODO order
    @Override
    public Optional<Order> getOrder(Long id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET)) {

            preparedStatement.setLong(0, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next()
                    ? Optional.of(mapRowToOrder(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getOrders() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Order> orders = new ArrayList<>();

            while(resultSet.next()) {
                orders.add(mapRowToOrder(resultSet));
            }

            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public Order updateOrder(Order order) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setLong(0, order.getId());
            preparedStatement.setLong(1, order.getUser().getId());
            preparedStatement.setString(2, order.getOrderStatus().name());
            preparedStatement.setDate(3, (Date) order.getCreatedAt());
            preparedStatement.setFloat(4, order.getTotalPrice());
            preparedStatement.executeUpdate();
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteOrder(Order order) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(0, order.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Order mapRowToOrder(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .id(resultSet.getLong("id"))
                .orderStatus(OrderStatus.valueOf(resultSet.getString("order_status")))
                .createdAt(resultSet.getDate("createdAt"))
                .totalPrice(resultSet.getFloat("totalPrice"))
                .build();
    }
}
