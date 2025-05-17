package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.service.impl.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class DishController {

    private final DishServiceImpl dishService;

    @Autowired
    public DishController(DishServiceImpl dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/menu")
    public String getCurrentMenu(Model model, Principal principal) {
        model.addAttribute("dishes", dishService.getAllCurrent());
        model.addAttribute("current_user", principal.getName());
        return "menu";
    }

    @GetMapping("/menu/all")
    public String getAllDishes(Model model, Principal principal) {
        model.addAttribute("dishes", dishService.getAll());
        model.addAttribute("current_user", principal.getName());
        return "menu";
    }

    @DeleteMapping("/menu/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return "redirect:/menu";
    }
}
