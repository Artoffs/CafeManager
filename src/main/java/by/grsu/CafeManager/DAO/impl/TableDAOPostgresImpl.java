package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.DAO.interfaces.TableDAO;
import by.grsu.CafeManager.model.Table;
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
public class TableDAOPostgresImpl implements TableDAO {

    @Value("${table.get}")
    private String GET;
    @Value("${table.getAll}")
    private String GET_ALL;
    @Value("${table.insert}")
    private String INSERT;
    @Value("${table.update}")
    private String UPDATE;
    @Value("${table.delete}")
    private String DELETE;

    private final DataSource dataSource;

    @Autowired
    public TableDAOPostgresImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Table> getTable(Long id) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("GET")) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.next()
                    ? Optional.of(mapRowToTable(rs))
                    : Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Table> getTables() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Table> tables = new ArrayList<>();

            while(resultSet.next()) {
                tables.add(mapRowToTable(resultSet));
            }
            resultSet.close();
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Table saveTable(Table table) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, table.getId());
            preparedStatement.setString(2, table.getPosition());
            preparedStatement.setString(3, table.getStatus().toString());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if(resultSet.next()) {
                    table.setId(resultSet.getLong(1));
                }
            }
            return table;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTable(Table table) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setLong(1, table.getId());
            preparedStatement.setString(2, table.getPosition());
            preparedStatement.setString(3, table.getStatus().toString());
            preparedStatement.setLong(4, table.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTable(Table table) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setLong(1, table.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Table mapRowToTable(ResultSet resultSet) throws SQLException {
        return Table.builder()
                .id(resultSet.getLong("id"))
                .position(resultSet.getString("table_number"))
                .status(TableStatus.valueOf(resultSet.getString("status")))
                .build();
    }
}
