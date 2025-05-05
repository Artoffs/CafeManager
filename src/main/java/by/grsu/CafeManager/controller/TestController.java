package by.grsu.CafeManager.controller;

import by.grsu.CafeManager.service.DTO.UserDTO;
import by.grsu.CafeManager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    private UserService userService;

    @Autowired
    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @ResponseBody
    public UserDTO testUser(@RequestParam String id) {
        return userService.getUser(Long.parseLong(id));
    }
}
