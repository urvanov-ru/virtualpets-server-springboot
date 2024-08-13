package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.PetType;


@Schema(description = "Информация о питомце.")
public record PetInfo(
        
        @Schema(description = "Идентификатор питомца.", example = "1")
        Integer id,
        
        @Schema(description = "Имя питомца.", example = "Мурка")
        String name,
        
        @Schema(description = "Тип питомца", example = "CAT")
        PetType petType) {
};
