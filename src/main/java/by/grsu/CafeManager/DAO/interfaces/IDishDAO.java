package by.grsu.CafeManager.DAO.interfaces;

import by.grsu.CafeManager.model.Dish;

import java.util.List;
import java.util.Optional;

public interface IDishDAO {
    Optional<Dish> getDish(Long id);
    List<Dish> getDishes();
    Dish saveDish(Dish dish);
    Dish updateDish(Dish dish);
    void deleteDish(Dish dish);
}
