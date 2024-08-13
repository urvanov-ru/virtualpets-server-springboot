package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация о пользователе.")
public record UserInfo(
        
        @Schema(
                description = "Идентификатор пользователя.",
                example = "1")
        int id,
        
        @Schema(
                description = "Полное имя пользователя",
                example = "Иванов Василий Петрович")
        String name) {
};
