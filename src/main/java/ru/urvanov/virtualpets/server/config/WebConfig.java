package ru.urvanov.virtualpets.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.urvanov.virtualpets.server.convserv.BookToApiConverter;
import ru.urvanov.virtualpets.server.convserv.ClothToApiConverter;
import ru.urvanov.virtualpets.server.convserv.DrinkToApiConverter;
import ru.urvanov.virtualpets.server.convserv.FoodToApiConverter;
import ru.urvanov.virtualpets.server.convserv.PetToApiConverter;

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
        registry.addConverter(new BookToApiConverter());
        registry.addConverter(new ClothToApiConverter());
        registry.addConverter(new DrinkToApiConverter());
        registry.addConverter(new FoodToApiConverter());
        registry.addConverter(new PetToApiConverter());
    }
}
