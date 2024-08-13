package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Выбор питомца на текущий сеанс игры.")
public record SelectPetArg(@Min(1) int petId) {
};
