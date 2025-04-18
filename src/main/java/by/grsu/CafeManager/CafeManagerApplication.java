package by.grsu.CafeManager;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@SpringBootApplication
public class CafeManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeManagerApplication.class, args);
	}

	@Bean
	public ApplicationRunner dataAccessChes(DataSource dataSource) {
		return args -> {
			try (Connection connection = dataSource.getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement("select * from test");
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					System.out.println(resultSet.getString(1));
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		};
    }

}
