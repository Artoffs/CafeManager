package by.grsu.CafeManager.DAO.impl;

import by.grsu.CafeManager.model.Ingredient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientDAOPostgresImpl  {
    private final DataSource dataSource;

    @Value("${ingredient.getAll}")
    private String GET_ALL;
    @Value("${ingredient.updateQuantity}")
    private String UPDATE_QUANTITY;

    public IngredientDAOPostgresImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Ingredient> getAll() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ALL);
             ResultSet rs = ps.executeQuery()) {
            List<Ingredient> ingredients = new ArrayList<>();
            while (rs.next()) {
                ingredients.add(Ingredient.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .quantity(rs.getBigDecimal("quantity"))
                        .unit(rs.getString("unit"))
                        .build());
            }
            return ingredients;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}