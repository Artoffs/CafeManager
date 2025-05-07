package by.grsu.CafeManager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("by.grsu.CafeManager")
@PropertySource({"classpath:application.properties", "classpath:sql.properties"})
public class MyConfig {

}
