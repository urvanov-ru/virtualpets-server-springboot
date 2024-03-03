/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.urvanov.virtualpets.server.Application;
import ru.urvanov.virtualpets.server.test.config.DaoTestConfig;
import ru.urvanov.virtualpets.server.test.listener.DaoTestExecutionListener;


/**
 * @author fedya
 *
 */
@Testcontainers
@ContextConfiguration(classes={Application.class, DaoTestConfig.class})
@TestExecutionListeners(value = {DaoTestExecutionListener.class}, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.liquibase.default-schema=virtualpets_springboot"
    })
@DirtiesContext // dirtiesContext нужен из-за dynamicPropertySource
public class AbstractDaoImplTest {


    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.1");
    
    
    @PersistenceContext
    protected EntityManager em;
    
    @Test
    public void test() {
    	System.out.println(em);
    }
}
