package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.IDishDAO;
import by.grsu.CafeManager.model.Dish;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DishDAOPostgresImpl implements IDishDAO {

    private static DataSource dataSource;

    @Override
    public Dish getDish(Long id) {
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
    public List<Dish> getDishes() {
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
    public Dish saveDish(Dish dish) {
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
    public Dish updateDish(Dish dish) {
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
    public void deleteDish(Dish dish) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("");
            boolean execute = preparedStatement.execute();
            if(execute) {
//                return User.builder().id(resultSet.getLong(0))...
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
