package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;

@Schema(description = "Добыча из лутбокса")
public record OpenBoxNewbieResult(int index,
        Map<BuildingMaterialId, Integer> buildingMaterials) {
};
