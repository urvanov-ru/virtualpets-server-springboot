package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;

@Schema(description = "Запрос на употребление пищи.")
public record SatietyArg(
        @Schema(
                description = "Еда из холодильника.",
                example = "DRY_FOOD")
        @NotNull FoodId foodId) {
};
