package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Отправка сооббщение в чат.")
public record SendChatMessageArg(
        @Schema(description = """
                Получатель сообщения. Может быть не указан. \
                Если получатель не указан или null, то сообщение \
                отправляется в общий чат.""",
                example = "1")
        Integer addresseeId,
        
        @Schema(description = "Сообщение", example = "Привет")
        String message) {
};
