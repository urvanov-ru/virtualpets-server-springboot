package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;

@Schema(description = "Добыча из лутбокса")
public record OpenBoxNewbieResult(
        
        @Schema(
                description = "Индекс лутбокса. Начинается с 0.",
                example = "0")
        int index,
        
        @Schema(
                description = "Полученные ресурсы",
                example = """
                        {"TIMBER": 2, "STONE": 3}
                        """)
        Map<BuildingMaterialId, Integer> buildingMaterials) {
};
