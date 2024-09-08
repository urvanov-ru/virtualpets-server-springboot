package ru.urvanov.virtualpets.server.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.urvanov.virtualpets.server.Application;
import ru.urvanov.virtualpets.server.test.config.DaoTestConfig;


@Testcontainers
@ContextConfiguration(classes={Application.class, DaoTestConfig.class})
@ActiveProfiles({"test", "test-dao"})
@DataJpaTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.liquibase.default-schema=virtualpets_server_springboot"
    })
@DirtiesContext // dirtiesContext нужен из-за dynamicPropertySource
class BaseDaoImplTest {

    /**
     * TestContainers PostgreSQL контейнер.
     */
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:16.1");
    
    
    @PersistenceContext
    protected EntityManager em;
    
    @Test
    void test() {
    }
}
