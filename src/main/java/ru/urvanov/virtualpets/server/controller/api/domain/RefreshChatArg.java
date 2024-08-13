package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на получение сообщений")
public record RefreshChatArg(
        
        @Schema(description = """
                Идентификатор последнего \
                полученного сообщения.
                """)
        Integer lastChatMessageId) {
};
