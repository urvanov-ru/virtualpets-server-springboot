package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

@Schema(description = "Информация о напитке в машине с напитками")
public record Drink(
        @Schema(description = "Идентификатор напитка", example = "WATER")
        DrinkId id,
        
        @Schema(description = """
                Уровень машины с напитками, на котором напиток \
                становится доступным. Уровни начинаются с 1.
                """,
                example = "1")
        int machineWithDrinksLevel,
        
        @Schema(description = """
                Порядок на полке машины с напитками. Индексация с 0.""",
                example = "0")
        int machineWithDrinksOrder,
        
        @Schema(description = """
                Количество напитка.
                """,
                example = "10")
        int count) {}
