package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.urvanov.virtualpets.server.dao.domain.PetType;

@Schema(description = "Информация о создаваемом питомце.")
public record CreatePetArg(
        
        @Schema(description = "Имя питомца", example = "Мурка")
        @NotNull @Size(min = 3, max = 50) String name,
        
        @Schema(
                description = "Тип питомца.",
                example = "CAT")
        @NotNull PetType petType,
        
        @Schema(
                description = "Произвольный комментарий",
                example = "Мой первый питомец")
        @Size(min = 1, max = 50) String comment) {
};
