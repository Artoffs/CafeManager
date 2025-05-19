package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.model.Dish;
import by.grsu.CafeManager.model.OrderForm;
import by.grsu.CafeManager.service.impl.DishServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/menu/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String create() {
        return "menu_create";
    }

    @PostMapping("/menu/create")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String submitOrder(@ModelAttribute Dish dish,
                              BindingResult result,
                              Model model,
                              Principal principal) {
        if (result.hasErrors()) {
            System.out.println(result);
            return "menu_create";
        }

        dishService.saveDish(dish);
        return "redirect:/menu";
    }

    @GetMapping("/menu/all")
    public String getAllDishes(Model model, Principal principal) {
        model.addAttribute("dishes", dishService.getAll());
        model.addAttribute("current_user", principal.getName());
        return "menu";
    }

    @DeleteMapping("/menu/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return "redirect:/menu";
    }
}
