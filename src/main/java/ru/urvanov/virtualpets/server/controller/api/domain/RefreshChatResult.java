package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = """
        Результат запроса на получение \
        списка сообщений чата.""")
public record RefreshChatResult(List<ChatMessage> chatMessages,
        Integer lastChatMessageId) {
};
