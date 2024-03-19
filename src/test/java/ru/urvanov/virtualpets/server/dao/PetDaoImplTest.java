/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.PetType;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class PetDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private PetDao petDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private LevelDao levelDao;
    
    @Autowired
    private FoodDao foodDao;

    @Autowired
    private Clock clock;
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    public void testSave() {
        long lastSize =  petDao.countByUserId(1);
        Pet pet = new Pet();
        pet.setName("test4y84hg4");
        pet.setCreatedDate(OffsetDateTime.now(clock));
        pet.setLoginDate(OffsetDateTime.now(clock));
        pet.setPetType(PetType.CAT);
        pet.setUser(userDao.findByLogin("Clarence").orElseThrow());
        pet.setLevel(levelDao.getReferenceById(1));
        petDao.save(pet);
        long newSize = petDao.countByUserId(1);
        assertEquals(lastSize + 1, newSize);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    public void testGetNewJournalEntriesCount() {
        Optional<Pet> pet = petDao.findById(1);
        Long newJournalEntriesCount = petDao.getPetNewJournalEntriesCount(pet.map(Pet::getId).orElseThrow());
        assertEquals(Long.valueOf(0L), newJournalEntriesCount);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    public void testAddJournalEntry() {
        Pet pet = petDao.findById(1).orElseThrow();
        PetJournalEntry petJournalEntry = new PetJournalEntry();
        petJournalEntry.setJournalEntry(JournalEntryId.EAT_SOMETHING);
        petJournalEntry.setReaded(true);
        
        pet.getJournalEntries().put(JournalEntryId.EAT_SOMETHING, petJournalEntry);
        //petJournalEntry.setPet(pet);
        petDao.save(pet);
        
        pet = petDao.findFullById(1).orElseThrow();
        assertTrue(pet.getJournalEntries().get(JournalEntryId.EAT_SOMETHING).getReaded());
    }
    
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetServiceImplTest.xls")
    @Test
    @Transactional
    public void testAddFood() {
        Pet pet = petDao.findById(1).orElseThrow();
        PetFood petFood = new PetFood();
        petFood.setPet(pet);
        petFood.setFood(foodDao.getReferenceById(FoodId.CARROT));
        petFood.setFoodCount(10);
        pet.getFoods().put(FoodId.CARROT,  petFood);
        petDao.save(pet);
        
        pet = petDao.findFullById(1).orElseThrow();
        assertEquals(10, pet.getFoods().get(FoodId.CARROT).getFoodCount());
    }

}
