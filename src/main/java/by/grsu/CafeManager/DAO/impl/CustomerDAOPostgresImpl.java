package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.model.Customer;
import org.springframework.beans.factory.annotation.Value;
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
public class CustomerDAOPostgresImpl{
    private final DataSource dataSource;

    @Value("${customer.getByPhone}")
    private String GET_BY_PHONE;
    @Value("{customer.getAll}")
    private String GET_ALL;

    public CustomerDAOPostgresImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Customer> getByPhone(String phone) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_BY_PHONE)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(Customer.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                        .bonusPoints(rs.getInt("bonus_points"))
                        .build());
            }
            return Optional.empty();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public List<Customer> getAll() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ALL)) {
            List<Customer> customers = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customers.add(Customer.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                        .bonusPoints(rs.getInt("bonus_points"))
                        .build());
            }
            return customers;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}