package ru.urvanov.virtualpets.server.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.urvanov.virtualpets.server.dao.ClothDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.PetFoodDao;
import ru.urvanov.virtualpets.server.dao.PetJournalEntryDao;
import ru.urvanov.virtualpets.server.dao.RoomDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Level;
import ru.urvanov.virtualpets.server.dao.domain.Pet;

@ExtendWith(MockitoExtension.class)
public class PetServiceImplJUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(PetServiceImplJUnitTest.class);

    private static final int LEVEL1_ID = 1;
    private static final int LEVEL1_EXPERIENCE = 0;
    private static final int LEVEL2_ID = 2;
    private static final int LEVEL2_EXPERIENCE = 10;
    
    @Mock
    private RoomDao roomDao;

    @Mock
    private PetDao petDao;

    @Mock
    private UserDao userDao;

    @Mock
    private PetFoodDao petFoodDao;

    @Mock
    private LevelDao levelDao;

    @Mock
    private ClothDao clothDao;

    @Mock
    private PetJournalEntryDao petJournalEntryDao;

    private ZoneId zoneId = ZoneId.of("Europe/Moscow");

    @Spy
    private Clock clock = Clock.fixed(ZonedDateTime
            .of(LocalDate.of(2024, 8, 14), LocalTime.of(16, 58), zoneId)
            .toInstant(), zoneId);

    @InjectMocks
    private PetServiceImpl service;
    
    @BeforeAll
    static void beforeAll() {
        logger.info("Before all tests in the class.");
    }
    
    @AfterAll
    static void afterAll() {
        logger.info("After all tests in the class.");
    }
    
    @BeforeEach
    void beforeEach() {
        logger.info("This method runs before each test.");
    }
    
    @AfterEach
    void afterEach() {
        logger.info("This method runs after each test.");
    }
    
    @Test
    void testAddExperience_ok() {
        Pet pet = new Pet();
        pet.setExperience(0);
        Level level1 = new Level(LEVEL1_ID, LEVEL1_EXPERIENCE);
        Level level2 = new Level(LEVEL2_ID, LEVEL2_EXPERIENCE);
        pet.setLevel(level1);
        when(levelDao.findById(LEVEL2_ID)).thenReturn(Optional.of(level2));
        service.addExperience(pet, 1);
        assertEquals(1, pet.getExperience());
    }
    
    @Test
    void testAddExperience_levelUp() {
        Pet pet = new Pet();
        pet.setExperience(9);
        Level level1 = new Level(LEVEL1_ID, LEVEL1_EXPERIENCE);
        Level level2 = new Level(LEVEL2_ID, LEVEL2_EXPERIENCE);
        pet.setLevel(level1);
        when(levelDao.findById(LEVEL2_ID)).thenReturn(Optional.of(level2));
        service.addExperience(pet, 1);
        assertEquals(10, pet.getExperience());
        assertEquals(level2, pet.getLevel());
    }
    
    @Test
    void testAddExperience_lastLevel() {
        Pet pet = new Pet();
        pet.setExperience(10);
        Level level1 = new Level(LEVEL1_ID, LEVEL1_EXPERIENCE);
        Level level2 = new Level(LEVEL2_ID, LEVEL2_EXPERIENCE);
        pet.setLevel(level2);
        when(levelDao.findById(LEVEL2_ID)).thenReturn(Optional.of(level2));
        when(levelDao.findById(LEVEL2_ID + 1)).thenReturn(Optional.empty());
        service.addExperience(pet, 1);
        assertEquals(10, pet.getExperience());
        assertEquals(level2, pet.getLevel());
    }
}
