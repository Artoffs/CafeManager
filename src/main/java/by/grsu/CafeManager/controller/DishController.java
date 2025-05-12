package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.service.impl.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DishController {

    private final DishServiceImpl dishService;

    @Autowired
    public DishController(DishServiceImpl dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/menu")
    public String getAllDishes(Model model) {
        model.addAttribute("dishes", dishService.getAll());
        return "menu";
    }
}
