package ru.urvanov.virtualpets.server.controller.api.domain;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = """
        Информация о собираемом предмете \
        в играх "Поиск скрытых предметов".
        """)
public record CollectObjectArg(
        
        @Schema(description = """
                Номер собираемого предмета. Индексация с 0.
                """)
        @NotNull @Min(0) Integer objectId
        ) {};
