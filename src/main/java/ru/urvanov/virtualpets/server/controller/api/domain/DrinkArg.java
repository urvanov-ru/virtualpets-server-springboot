package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

@Schema(description = """
        Информация о выпиваемом напитке из машины с напитками.
        """)
public record DrinkArg(
        
        @Schema(
                description = "Код выпиваемого напитка.",
                example = "WATER")
        @NotNull DrinkId drinkId) {};
