package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.DishDAO;
import by.grsu.CafeManager.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Completed

@Repository
public class DishDAOPostgresImpl implements DishDAO {

    @Value("${dish.get}")
    private String GET_BY_ID;
    @Value("${dish.getAll}")
    private String GET_CURRENT;
    @Value("${dish.getAllIncludeDeleted}")
    private String GET_ALL;
    @Value("${dish.insert}")
    private String INSERT;
    @Value("${dish.update}")
    private String UPDATE;
    @Value("${dish.delete}")
    private String DELETE;

    private final DataSource dataSource;

    @Autowired
    public DishDAOPostgresImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Dish> getDish(Long id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.next()
                    ? Optional.of(mapRowToDish(rs))
                    : Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dish> getDishes() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet rs = preparedStatement.executeQuery()) {

            List<Dish> dishList = new ArrayList<>();

            while(rs.next()) {
                dishList.add(mapRowToDish(rs));
            }
            rs.close();
            return dishList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dish> getCurrentDishes() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_CURRENT);
            ResultSet rs = preparedStatement.executeQuery()) {

            List<Dish> dishList = new ArrayList<>();

            while(rs.next()) {
                dishList.add(mapRowToDish(rs));
            }
            rs.close();
            return dishList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish saveDish(Dish dish) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2, dish.getDescription());
            preparedStatement.setFloat(3, dish.getPrice());
            preparedStatement.setString(4, dish.getCategory());
            preparedStatement.setBoolean(5, dish.isAvailable());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    dish.setId(resultSet.getLong(1));
                }
            }

            return dish;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить блюдо",e);
        }
    }

    @Override
    public void updateDish(Dish dish) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setLong(1, dish.getId());
            preparedStatement.setString(2, dish.getName());
            preparedStatement.setString(3, dish.getName());
            preparedStatement.setFloat(4, dish.getPrice());
            preparedStatement.setString(5, dish.getName());
            preparedStatement.setBoolean(6, dish.isAvailable());
            preparedStatement.setLong(7, dish.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteDish(Long id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Dish mapRowToDish(ResultSet rs) throws SQLException {
        return Dish.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .price(rs.getFloat("price"))
                .category(rs.getString("category"))
                .isAvailable(rs.getBoolean("is_available"))
                .isDeleted(rs.getBoolean("is_deleted"))
                .build();
    }
}
