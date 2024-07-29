package integration;

import config.TestConfig;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

@Transactional
@ExtendWith(SpringExtension.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ContextConfiguration(classes = {TestConfig.class})
@Sql(
        value = {"classpath:cats-dao-test.sql"},
        config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
)
public abstract class IntegrationTestBase {
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(@NotNull DynamicPropertyRegistry registry) {
        registry.add("en.pchz.data.jpa.jdbc.url", container::getJdbcUrl);
    }
}
