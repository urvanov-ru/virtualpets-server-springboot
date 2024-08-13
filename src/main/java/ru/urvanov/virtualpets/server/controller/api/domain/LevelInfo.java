package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Уровень и опыт игрока.")
public record LevelInfo(
        
        @Schema(description = "Уровень игрока.", example = "1")
        Integer level,
        
        @Schema(description = "Опыт игрока.", example = "2")
        Integer experience,
        
        @Schema(
                description = "Минимальный опыт текущего уровня.",
                example = "0")
        Integer minExperience,
        
        @Schema(
                description = "Минимальный опыт текущего уровня.",
                example = "10")
        Integer maxExperience) {
};
