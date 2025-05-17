package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.OrderItemDAO;
import by.grsu.CafeManager.model.Dish;
import by.grsu.CafeManager.model.Order;
import by.grsu.CafeManager.model.OrderForm;
import by.grsu.CafeManager.model.OrderItem;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderItemDAOPostgresImpl implements OrderItemDAO {

    @Value("${orderItem.get}")
    private String GET;
    @Value("${orderItem.getAll}")
    private String GET_ALL;
    @Value("${orderItem.insert}")
    private String INSERT;
    @Value("${orderItem.update}")
    private String UPDATE;
    @Value("${orderItem.delete}")
    private String DELETE;
    @Value("${orderItem.getByOrderId}")
    private String GET_BY_ORDER_ID;


    private final DataSource dataSource;

    @Autowired
    public OrderItemDAOPostgresImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<OrderItem> getOrderItem(Long id) {
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next()
                    ? Optional.of(mapRowToOrderItem(resultSet))
                    : Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderItem> getOrderItems() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            List<OrderItem> orderItems = new ArrayList<>();

            while (resultSet.next()) {
                orderItems.add(mapRowToOrderItem(resultSet));
            }
            return orderItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderItem> getOrderItemsByOrderId(Long id) {
        List<OrderItem> orderItems = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ORDER_ID)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orderItems.add(mapRowToOrderItem(resultSet));
            }
            return orderItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveOrderItem(OrderForm.OrderItemDto orderItem) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            preparedStatement.setLong(1, orderItem.getOrderId());
            preparedStatement.setLong(2, orderItem.getDishId());
            preparedStatement.setInt(3, orderItem.getQuantity());
            preparedStatement.setString(4, orderItem.getComment());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, orderItem.getId());
            preparedStatement.setLong(2, orderItem.getOrder().getId());
            preparedStatement.setLong(3, orderItem.getDish().getId());
            preparedStatement.setInt(4, orderItem.getQuantity());
            preparedStatement.setString(5, orderItem.getComment());
            preparedStatement.setLong(5, orderItem.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteOrderItem(OrderItem orderItem) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(1, orderItem.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private OrderItem mapRowToOrderItem(ResultSet rs) throws SQLException {

        User user = User.builder()
                .id(rs.getLong("user_id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .role(Role.valueOf(rs.getString("role")))
                .build();

        Table table = Table.builder()
                .id(rs.getLong("table_id"))
                .position(rs.getString("table_number"))
                .status(TableStatus.valueOf(rs.getString("table_status")))
                .build();

        Dish dish = Dish.builder()
                .id(rs.getLong("dish_id"))
                .name(rs.getString("dish_name"))
                .description(rs.getString("description"))
                .price(rs.getFloat("price"))
                .category(rs.getString("category"))
                .isAvailable(rs.getBoolean("is_available"))
                .build();

        Order order = Order.builder()
                .id(rs.getLong("order_id"))
                .table(table)
                .user(user)
                .orderStatus(OrderStatus.valueOf(rs.getString("order_status")))
                .createdAt(rs.getDate("created_at"))
                .build();


        return OrderItem.builder()
                .id(rs.getLong("order_item_id"))
                .order(order)
                .dish(dish)
                .quantity(rs.getInt("quantity"))
                .comment(rs.getString("comment"))
                .build();
    }
}
