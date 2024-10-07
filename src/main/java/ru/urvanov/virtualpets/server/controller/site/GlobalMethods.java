package ru.urvanov.virtualpets.server.controller.site;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import ru.urvanov.virtualpets.server.service.exception.PetNotFoundException;
import ru.urvanov.virtualpets.server.service.exception.UserNotFoundException;
/**
 * Содержит общие методы 
 * @ExceptionHandler, @InitBinder, @ModelAttribute
 * всех контроллеров.
 */
@ControllerAdvice
public class GlobalMethods {
    
    /**
     * Добавляет атрибут menu_play_url к модели всех контроллеров. 
     * Атрибут menu_play_url используется фрагментом
     * src/main/resources/templates/fragments/menu.html
     * в качестве ссылки на клиентскую часть игры.
     * @param playUrl Ссылка на клиентскую часть игры.
     * @return Значение для атрибута menu_play_url
     */
    @ModelAttribute("menu_play_url")
    public String menuPlayUrl(
            @Value("${virtualpets-server-springboot.play.url}")
            String playUrl) {
        return playUrl;
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason = "User was not found.")
    public void userNotFound() throws NoHandlerFoundException {
    }

    @ExceptionHandler(PetNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason = "Pet was not found.")
    public void petNotFound() throws NoHandlerFoundException {
    }

}
