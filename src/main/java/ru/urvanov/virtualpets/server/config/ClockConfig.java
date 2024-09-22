package ru.urvanov.virtualpets.server.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * конфигурация {@link Clock}
 */
@Configuration
public class ClockConfig {
    
    /**
     * Классы пакета {@link java.time} используют этот экземпляр Clock
     * при создании экземпляров с текущей датой и временем. Подобная
     * практика позволяет подставить в тесты другой Clock,
     * возвращающий всегда фиксированное значение.
     * @return Экземпляр {@link Clock}
     */
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
