package config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    @Value("${en.pchz.data.jpa.jdbc.url}")
    private String jdbcUrl;
    @Value("${en.pchz.data.jpa.jdbc.user}")
    private String user;
    @Value("${en.pchz.data.jpa.jdbc.password}")
    private String password;
    @Value("${en.pchz.data.jpa.packages}")
    private String packages;
}
