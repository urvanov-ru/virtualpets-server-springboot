package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тип одежды питомца")
public enum ClothType {
    HAT,
    CLOTH,
    BOW
}
