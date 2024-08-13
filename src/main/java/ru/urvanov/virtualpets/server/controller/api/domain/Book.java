package ru.urvanov.virtualpets.server.controller.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Книга в книжном шкафу")
public record Book(
        
        @Schema(description = "Код книги", example = "DESTINY")
        String id,
        
        @Schema(description = """
                Полка в книжном шкафу, на которой лежит книга. \
                Уровень книжного шкафа, требуемый для книги.  \
                Уровни книжных шкафов начинаются с 1.
                """,
                example = "1"
        )
        int bookcaseLevel,
        
        @Schema(description = """
                Порядок книги на полке. \
                Номерация с 0. \
                """,
                example = "0")
        int bookcaseOrder) {}
