package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация об аутентифицированном пользователе.")
public record LoginResult(
        
        @Schema(description = "Флаг успешности", example = "true")
        boolean success,
        
        @Schema(description = "Сообщение сервера.")
        String message,
        
        @Schema(description = "Идентификатор игрока", example = "1")
        Integer userId,
        
        @Schema(description = "Логин", example = "user")
        String login,
        
        @Schema(description = "Полное имя", example = "Tester")
        String name) {
};
