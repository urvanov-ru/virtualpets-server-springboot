package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Список питомцев")
public record PetListResult(List<PetInfo> petsInfo) {
};
