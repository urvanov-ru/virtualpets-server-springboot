package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.AchievementId;

@Schema(description = "Состояние игры к локации \"Город\".")
public record GetTownInfoResult(
        
        @Schema(
                description = """
                        Количество новых, непрочитанных записей \
                        в дневнике.""")
        long newJournalEntriesCount,
        
        @Schema(description = "Уровень питомца и опыт.")
        LevelInfo levelInfo,
        
        @Schema(description = "Новые полученные достижения.")
        List<AchievementId> achievements) {
};
