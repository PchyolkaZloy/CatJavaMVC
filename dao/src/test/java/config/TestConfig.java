package config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "en.pchz.lab4.repository")
@EnableJpaRepositories(basePackages = "en.pchz.lab4.repository")
@EntityScan(basePackages = "en.pchz.lab4.entity")
@Import(OrmConfig.class)
public class TestConfig {
}