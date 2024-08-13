package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Записи из дневника питомца.")
public record GetPetJournalEntriesResult(List<PetJournalEntry> entries) {
};
