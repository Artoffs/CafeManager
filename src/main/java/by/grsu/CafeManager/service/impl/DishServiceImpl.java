package by.grsu.CafeManager.service.impl;

import by.grsu.CafeManager.DAO.interfaces.DishDAO;
import by.grsu.CafeManager.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl {

    private final DishDAO dishDAO;

    @Autowired
    public DishServiceImpl(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }

    public List<Dish> getAll() {
        return dishDAO.getDishes();
    }

    public List<Dish> getAllCurrent() {
        return dishDAO.getCurrentDishes();
    }

    public void saveDish(Dish dish) {
        dishDAO.saveDish(dish);
    }

    public void deleteDish(Long id) {
        dishDAO.deleteDish(id);
    }

    public void updateDish(Dish dish) {
        dishDAO.updateDish(dish);
    }
}
