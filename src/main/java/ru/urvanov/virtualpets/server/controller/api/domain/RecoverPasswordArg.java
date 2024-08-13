package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Восстановление пароля")
public record RecoverPasswordArg(
        
        @Schema(description = "Логин", example = "user")
        @NotNull @Size(min = 1, max = 50) String login,
        
        @Schema(description = "email", example = "vasya@example.com")
        @NotNull @Size(min = 3, max = 50) String email,
        
        @Schema(description = "Версия API", example = "0.21")
        @NotNull @Size(min = 1, max = 50) String version) {
};
