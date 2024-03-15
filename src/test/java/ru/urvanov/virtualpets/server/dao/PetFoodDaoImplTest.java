/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class PetFoodDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private PetFoodDao petFoodDao;
    
    @Autowired
    private FoodDao foodDao;
    
    @Autowired
    private PetDao petDao;
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindByPetId() {
        Iterable<PetFood> foods = petFoodDao.findByPetId(1);
        assertEquals(IterableUtils.size(foods), 4);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindByPet() {
        Pet pet = petDao.findById(1).orElseThrow();
        assertNotNull(pet);
        Iterable<PetFood> foods = petFoodDao.findByPet(pet);
        assertEquals(IterableUtils.size(foods), 4);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindById() {
        PetFood food = petFoodDao.findById(10).orElseThrow();
        assertNotNull(food);
        assertEquals(food.getId(), Integer.valueOf(10));
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testSave() {
        Food foodCarrot = foodDao.findById(FoodType.CARROT).orElseThrow();
        PetFood petFood = new PetFood();
        Pet pet = petDao.getReferenceById(1);
        petFood.setFood(foodCarrot);
        petFood.setFoodCount(100);
        petFood.setPet(pet);
        petFoodDao.save(petFood);
        assertNotNull(petFood.getId());
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindByPetIdAndFoodType() {
        Optional<PetFood> food = petFoodDao.findByPetIdAndFoodType(1, FoodType.DRY_FOOD);
        assertThat(food).isPresent();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/PetFoodServiceImplTest.xls")
    @Test
    public void testFindByPetIdAndFoodType2() {
        Optional<PetFood> food = petFoodDao.findByPetIdAndFoodType(13463456, FoodType.CHOCOLATE);
        assertThat(food).isEmpty();
    }
}
