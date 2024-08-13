package ru.urvanov.virtualpets.server.controller.api.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;

@Schema(description = "Цены на строительство.")
public class RoomBuildMenuCosts implements Serializable {
    private static final long serialVersionUID = -7317824338376286772L;
    
    @Schema(description = """
            Материалы, необходимые для строительства холодильника.""")
    private List<Map<BuildingMaterialId, Integer>> refrigeratorCosts;
    
    @Schema(description = """
            Материалы, необходимые для строительства машины \
            с напитками.""")
    private List<Map<BuildingMaterialId, Integer>> machineWithDrinksCosts;
    
    @Schema(description = """
            Материалы, необходимые для строительства книжного шкафа.""")
    private List<Map<BuildingMaterialId, Integer>> bookcaseCosts;

    public List<Map<BuildingMaterialId, Integer>> getRefrigeratorCosts() {
        return refrigeratorCosts;
    }

    public void setRefrigeratorCosts(
            List<Map<BuildingMaterialId, Integer>> refrigeratorCosts) {
        this.refrigeratorCosts = refrigeratorCosts;
    }

    public List<Map<BuildingMaterialId, Integer>> getMachineWithDrinksCosts() {
        return machineWithDrinksCosts;
    }

    public void setMachineWithDrinksCosts(
            List<Map<BuildingMaterialId, Integer>> machineWithDrinksCosts) {
        this.machineWithDrinksCosts = machineWithDrinksCosts;
    }

    public List<Map<BuildingMaterialId, Integer>> getBookcaseCosts() {
        return bookcaseCosts;
    }

    public void setBookcaseCosts(
            List<Map<BuildingMaterialId, Integer>> bookcaseCosts) {
        this.bookcaseCosts = bookcaseCosts;
    }

}
