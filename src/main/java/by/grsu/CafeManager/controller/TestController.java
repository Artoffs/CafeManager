package by.grsu.CafeManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class TestController {

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        if (principal != null) model.addAttribute("current_user", principal.getName());
        return "home";
    }
}
