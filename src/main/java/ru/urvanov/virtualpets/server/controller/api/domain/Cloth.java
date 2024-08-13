package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.ClothType;

@Schema(description = "Одежда питомца в гардеробе")
public record Cloth(
        
        @Schema(description = "Код одежды", example = "BLUE_BOW")
        String id,
        
        @Schema(description = """
                Тип одежды. Возможные значения: HAT, CLOTH, BOW.""",
                example = "BOW")
        ClothType clothType,
        
        @Schema(description = """
                Порядок отображения в гардеробе. \
                Индексация начинается с 0.""",
                example = "1")
        int wardrobeOrder) {}
