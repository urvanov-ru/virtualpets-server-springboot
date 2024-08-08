package ru.urvanov.virtualpets.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import ru.urvanov.virtualpets.server.convserv.BookToApiConverter;
import ru.urvanov.virtualpets.server.convserv.ClothToApiConverter;
import ru.urvanov.virtualpets.server.convserv.DrinkToApiConverter;
import ru.urvanov.virtualpets.server.convserv.FoodToApiConverter;
import ru.urvanov.virtualpets.server.convserv.PetToApiConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("site/download")
                .setViewName("download");
        registry.addViewController("site/information")
                .setViewName("information/list");
        registry.addViewController("site/information/gameHelp")
                .setViewName("information/gameHelp");
        registry.addViewController("site/information/gameHelp")
                .setViewName("information/gameHelp");
        registry.addViewController("site/login")
                .setViewName("login");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/rest/**").allowCredentials(true)
            .allowedOrigins("""
                    http://localhost:8081, \
                    http://localhost:8081/, \
                    http://virtualpets.urvanov.ru""")
            .allowCredentials(true);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BookToApiConverter());
        registry.addConverter(new ClothToApiConverter());
        registry.addConverter(new DrinkToApiConverter());
        registry.addConverter(new FoodToApiConverter());
        registry.addConverter(new PetToApiConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver("locale");
    }
}
