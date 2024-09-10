package ru.urvanov.virtualpets.server.dao;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.urvanov.virtualpets.server.Application;
import ru.urvanov.virtualpets.server.config.ClockConfig;
import ru.urvanov.virtualpets.server.config.DaoTestConfig;
import ru.urvanov.virtualpets.server.config.DataSourceConfig;

/**
 * Базовый класс для тестов слоя DAO
 */
@Testcontainers
@ContextConfiguration(classes = {
        Application.class, DaoTestConfig.class, ClockConfig.class,
        DataSourceConfig.class})
@ActiveProfiles({"test", "test-dao"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BaseDaoImplTest {

    /**
     * Управляет запуском и остановкой контейнера PostgreSQL.
     * При запуске нескольких тестов контейнер создаётся один раз
     * и переиспользуется последующими тестами.
     */
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:16.1");

}
