package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Одежда питомца в гардеробной")
public record GetPetClothsResult(
        
        @Schema(
                description = "Код надетой шляпы.",
                example = "COWBOY_HAT")
        String hatId,
        
        @Schema(
                description = "Код надетой одежды.",
                example = "COLORED_BODY")
        String clothId,
        
        @Schema(
                description = "Код надетого бантика.",
                example = "BLUE_BOW")
        String bowId,
        
        @Schema(description = "Список имеющейся у питомца одежды.")
        List<Cloth> cloths) {
};
