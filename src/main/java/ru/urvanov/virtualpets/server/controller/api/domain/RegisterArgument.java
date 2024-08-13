package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос регистрации нового пользователя")
public record RegisterArgument(
        
        @Schema(description = "Логин", example = "vasya2024")
        @NotNull @Size(min = 3, max = 50) String login,
        
        @Schema(
                description = "Полное имя пользователя",
                example = "Иванов Василий Петрвоич")
        @NotNull @Size(min = 3, max = 50) String name,
        
        @Schema(description = "Пароль", example = "123")
        @NotNull @Size(min = 3, max = 50) String password,
        
        @Schema(description = "E-mail", example = "vasya@example.com")
        @NotNull @Size(min = 3, max = 50) String email,
        
        @Schema(description = "Версия API", example = "0.21")
        @NotNull @Size(min = 1, max = 50) String version) {
};
