package ru.urvanov.virtualpets.server.controller.site;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

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

}
