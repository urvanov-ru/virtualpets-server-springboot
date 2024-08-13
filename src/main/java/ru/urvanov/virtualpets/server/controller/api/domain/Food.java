package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;

@Schema(description = "Еда из холодильника.")
public record Food(
        
        @Schema(description = "Код еды.", example = "DRY_FOOD")
        FoodId id,
        
        @Schema(description = """
                Уровень холодильника, необходимый для хранения \
                этого блюда. Уровни холодильника начинаются с 1.""",
                example = "1")
        int refrigeratorLevel,
        
        @Schema(description = """
                Порядок еды на полке холодильника. \
                Индексация с 0.""")
        int refrigeratorOrder,
        
        @Schema(description = "Количество еды.", example = "10")
        int count) {}
