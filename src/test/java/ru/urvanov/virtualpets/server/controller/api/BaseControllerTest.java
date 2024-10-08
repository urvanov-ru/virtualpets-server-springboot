package ru.urvanov.virtualpets.server.controller.api;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Базовый класс для тестов MockMvc слоя контроллеров.
 */
@ExtendWith(SpringExtension.class)
@Testcontainers
@SpringBootTest
@ActiveProfiles({"test", "test-mock-mvc"})
abstract class BaseControllerTest {
    
    /**
     * Конфигурация веб-приложения. С помощью 
     * {@code wac.getServletContext()} осуществляется доступ к экземпляру
     * {@link jakarta.servlet.ServletContext}
     */
    @Autowired
    protected WebApplicationContext wac;

    /**
     * Методы наследников используют это поле для выполнения запросов:
     *  <pre>{@code
     *     mockMvc.perform(...
     * }</pre>
     */
    protected MockMvc mockMvc;

    /**
     * Управляет запуском и остановкой контейнера PostgreSQL.
     * При запуске нескольких тестов контейнер создаётся один раз
     * и переиспользуется последующими тестами.
     */
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:16.1");

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }
}
