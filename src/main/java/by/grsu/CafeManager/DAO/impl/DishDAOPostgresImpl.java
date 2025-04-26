package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.IDishDAO;
import by.grsu.CafeManager.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DishDAOPostgresImpl implements IDishDAO {

    private final DataSource dataSource;

    @Autowired
    public DishDAOPostgresImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Dish> getDish(Long id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("");
            ResultSet rs = preparedStatement.executeQuery()) {

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
            PreparedStatement preparedStatement = connection.prepareStatement("");
            ResultSet rs = preparedStatement.executeQuery()) {

            List<Dish> dishList = new ArrayList<>();

            while(rs.next()) {
                dishList.add(mapRowToDish(rs));
            }
            return dishList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish saveDish(Dish dish) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("")) {

            preparedStatement.executeUpdate();

            return dish;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish updateDish(Dish dish) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("")) {

            preparedStatement.executeUpdate();

            return dish;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteDish(Dish dish) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("")) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Dish mapRowToDish(ResultSet rs) throws SQLException {
        return Dish.builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .description(rs.getString(3))
                .price(rs.getFloat(4))
                .isAvailable(rs.getBoolean(5))
                .build();
    }
}
