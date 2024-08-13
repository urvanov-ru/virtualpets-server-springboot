package ru.urvanov.virtualpets.server.controller.api.domain;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;

@Schema(description = "Запись из дневника питомца.")
public record PetJournalEntry(
        
        @Schema(
                description = "Дата и время добавления записи",
                example = "2024-08-13T12:55:25.692864Z")
        OffsetDateTime createdAt,
        
        @Schema(
                description = "Код записи в дневнике питомца.",
                example = "OPEN_NEWBIE_BOXES")
        JournalEntryId id) implements Comparable<PetJournalEntry> {

    @Override
    public int compareTo(PetJournalEntry o) {
        return this.createdAt.compareTo(o.createdAt);
    }


};
