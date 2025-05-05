package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.OrderDAO;
import by.grsu.CafeManager.model.Order;
import by.grsu.CafeManager.model.Table;
import by.grsu.CafeManager.model.User;
import by.grsu.CafeManager.model.enums.OrderStatus;
import by.grsu.CafeManager.model.enums.Role;
import by.grsu.CafeManager.model.enums.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAOPostgresImpl implements OrderDAO {

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

    @Override
    public Optional<Order> getOrder(Long id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET)) {

            preparedStatement.setLong(1, id);
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

            resultSet.close();
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order saveOrder(Order order) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, order.getTable().getId());
            preparedStatement.setLong(2, order.getUser().getId());
            preparedStatement.setString(3, order.getOrderStatus().toString());
            preparedStatement.setDate(4, (Date) order.getCreatedAt());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    order.setId(resultSet.getLong(1));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void updateOrder(Order order) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setLong(1, order.getId());
            preparedStatement.setLong(2, order.getTable().getId());
            preparedStatement.setLong(3, order.getUser().getId());
            preparedStatement.setString(4, order.getOrderStatus().name());
            preparedStatement.setDate(5, (Date) order.getCreatedAt());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteOrder(Order order) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(0, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Order mapRowToOrder(ResultSet resultSet) throws SQLException {

        Table table = Table.builder()
                .id(resultSet.getLong("table_id"))
                .position(resultSet.getString("table_number"))
                .status(TableStatus.valueOf(resultSet.getString("table_status")))
                .build();

        User user = User.builder()
                .id(resultSet.getLong("user_id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .role(Role.valueOf(resultSet.getString("role")))
                .build();

        return Order.builder()
                .id(resultSet.getLong("id"))
                .table(table)
                .user(user)
                .orderStatus(OrderStatus.valueOf(resultSet.getString("order_status")))
                .createdAt(resultSet.getDate("createdAt"))
                .build();
    }
}
