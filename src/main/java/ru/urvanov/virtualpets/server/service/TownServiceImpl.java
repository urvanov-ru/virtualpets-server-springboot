package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.controller.api.domain.GetTownInfoResult;
import ru.urvanov.virtualpets.server.controller.api.domain.LevelInfo;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.domain.AchievementId;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.Level;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Service
public class TownServiceImpl implements TownApiService {

    @Autowired
    private ru.urvanov.virtualpets.server.service.PetService petService;

    @Autowired
    private LevelDao levelDao;
    
    @Autowired
    private PetDao petDao;

    @Autowired
    private Clock clock;

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public GetTownInfoResult getTownInfo(UserPetDetails userPetDetails)
            throws ServiceException {
        Pet pet = petDao.findByIdWithJournalEntriesAndAchievements(
                userPetDetails.petId()).orElseThrow();

        Map<JournalEntryId, PetJournalEntry> mapJournalEntries = pet
                .getJournalEntries();
        if (!mapJournalEntries.containsKey(JournalEntryId.PLAY_HIDDEN_OBJECT_GAMES)) {
            PetJournalEntry petJournalEntry = new PetJournalEntry();
            petJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            petJournalEntry.setPet(pet);
            petJournalEntry.setJournalEntry(JournalEntryId.PLAY_HIDDEN_OBJECT_GAMES);
            mapJournalEntries.put(JournalEntryId.PLAY_HIDDEN_OBJECT_GAMES, petJournalEntry);
        }

        petService.addAchievementIfNot(pet, AchievementId.LEAVE_ROOM);
        
        petService.addExperience(pet, 1);
        
        int levelId = pet.getLevel().getId();
        int experience = pet.getExperience();
        Optional<Level> nextLevelLeague = levelDao
                .findById(pet.getLevel().getId() + 1);
        int maxExperience = nextLevelLeague.map(Level::getExperience)
                .orElse(Integer.MAX_VALUE);
        int minExperience = pet.getLevel().getExperience();
        LevelInfo levelInfo = new LevelInfo(levelId, experience, minExperience, maxExperience);

        List<AchievementId> achievements = petService
                .calculateAchievements(pet);

        long newJournalEntriesCount = petService
                .getPetNewJournalEntriesCount(pet.getId());
        return new GetTownInfoResult(newJournalEntriesCount, levelInfo, achievements);
    }

}
