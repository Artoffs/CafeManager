package by.grsu.CafeManager;

import by.grsu.CafeManager.service.interfaces.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class CafeManagerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CafeManagerApplication.class, args);
        UserService userService = run.getBean("userService", UserService.class);
        System.out.println(userService.getUser(34L));
    }
}
