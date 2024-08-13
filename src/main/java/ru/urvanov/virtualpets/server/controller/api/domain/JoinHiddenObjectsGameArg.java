package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на подключение к игре или создание новой.")
public record JoinHiddenObjectsGameArg(
        @NotNull HiddenObjectsGameType hiddenObjectsGameType) {
}
