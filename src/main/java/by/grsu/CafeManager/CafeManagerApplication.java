package by.grsu.CafeManager;

import by.grsu.CafeManager.DAO.impl.OrderItemDAOPostgresImpl;
import by.grsu.CafeManager.DAO.interfaces.IOrderItemDAO;
import by.grsu.CafeManager.DAO.interfaces.IUserDAO;
import by.grsu.CafeManager.model.User;
import by.grsu.CafeManager.model.enums.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class CafeManagerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CafeManagerApplication.class, args);
        IUserDAO userDAO = run.getBean("dao", IUserDAO.class);
        User artoffchik = User.builder()
                .username("Artoffchik1")
                .password("123123")
                .role(Role.ADMIN)
                .build();
        System.out.println(artoffchik);
        userDAO.saveUser(artoffchik);
        System.out.println(artoffchik);
    }
}
