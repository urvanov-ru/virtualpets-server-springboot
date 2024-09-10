package ru.urvanov.virtualpets.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
/**
 * Конфигурация Spring для тестирования слоя DAO отдельно от всех
 * остальных слоёв приложения.
 */
@Configuration
@ComponentScan(basePackages = {"ru.urvanov.virtualpets.server.dao"})
@Profile("test-dao")
public class DaoTestConfig {
    
}
