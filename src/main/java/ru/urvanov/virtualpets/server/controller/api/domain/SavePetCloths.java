package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на сохранение нового набора одежды.")
public record SavePetCloths(
        @Size(min = 1, max = 50) String hatId,
        @Size(min = 1, max = 50) String clothId,
        @Size(min = 1, max = 50) String bowId) {
};

