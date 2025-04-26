package by.grsu.CafeManager;

import by.grsu.CafeManager.DAO.impl.UserDAOPostgresImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class CafeManagerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CafeManagerApplication.class, args);
        UserDAOPostgresImpl dao = run.getBean("dao", UserDAOPostgresImpl.class);
        System.out.println(dao.getUser(1L));
    }
}
