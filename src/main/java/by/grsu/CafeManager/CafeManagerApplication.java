package by.grsu.CafeManager;

import by.grsu.CafeManager.DAO.impl.OrderItemDAOPostgresImpl;
import by.grsu.CafeManager.DAO.impl.UserDAOPostgresImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class CafeManagerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CafeManagerApplication.class, args);
        OrderItemDAOPostgresImpl dao = run.getBean("orderItemDAOPostgresImpl", OrderItemDAOPostgresImpl.class);
        System.out.println(dao.getOrderItems());
    }
}
