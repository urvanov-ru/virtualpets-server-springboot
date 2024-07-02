package ru.urvanov.virtualpets.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("site/download").setViewName("download");
        registry.addViewController("site/information").setViewName("information/list");
        registry.addViewController("site/information/gameHelp").setViewName("information/gameHelp");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/rest/**").allowCredentials(true).allowedOrigins("http://localhost:8081,http://localhost:8081/").allowCredentials(true);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new PetTypeServerToSharedConverter());
//        registry.addConverter(new PetTypeSharedToServerConverter());
//        registry.addConverter(new RoleServerToSharedConverter());
//        registry.addConverter(new RoleSharedToServerConverter());
//        registry.addConverter(new FoodTypeServerToSharedConverter());
//        registry.addConverter(new FoodTypeSharedToServerConverter());
//        registry.addConverter(new ClothTypeServerToSharedConverter());
//        registry.addConverter(new ClothTypeSharedToServerConverter());
//        registry.addConverter(new SexServerToSharedConverter());
//        registry.addConverter(new SexSharedToServerConverter());
//        registry.addConverter(new BuildingMaterialTypeServerToSharedConverter());
//        registry.addConverter(new BuildingMaterialTypeSharedToServerConverter());
//        registry.addConverter(new DrinkTypeServerToSharedConverter());
//        registry.addConverter(new DrinkTypeSharedToServerConverter());
//        registry.addConverter(new JournalEntryTypeSharedToServerConverter());
//        registry.addConverter(new JournalEntryTypeServerToSharedConverter());
//        registry.addConverter(new AchievementCodeServerToSharedConverter());
//        registry.addConverter(new AchievementCodeSharedToServerConverter());
    }
}
