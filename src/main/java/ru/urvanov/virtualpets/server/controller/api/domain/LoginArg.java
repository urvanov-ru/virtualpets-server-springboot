package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Информация о пользователе, необходимая для входа")
public record LoginArg(
        
        @Schema(description = "Логин пользователя.", example = "user")
        @NotNull @Size(min = 3, max = 50) String login,
        
        @Schema(description = "Пароль пользователя.", example = "123")
        @NotNull @Size(min = 1, max = 50) String password,
        
        @Schema(description = "Версия API.", example = "0.21")
        @NotNull @Size(min = 1, max = 50) String version) {
};
