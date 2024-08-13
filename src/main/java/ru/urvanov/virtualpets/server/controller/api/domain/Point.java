package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Координата.")
public record Point(
        @Schema(description = "Позиция по горизонтали.", example = "1")
        @NotNull int x,
        
        @Schema(description = "Позиция по вертикали", example = "10")
        @NotNull int y) {
};
