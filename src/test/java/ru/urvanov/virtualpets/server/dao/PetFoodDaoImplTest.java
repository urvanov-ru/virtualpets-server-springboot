package ru.urvanov.virtualpets.server.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;

@Sql({ "/ru/urvanov/virtualpets/server/clean.sql",
        "PetFoodDaoImplTest.sql" })
class PetFoodDaoImplTest extends BaseDaoImplTest {
    
    @Autowired
    private PetFoodDao petFoodDao;
    
    @Autowired
    private FoodDao foodDao;
    
    @Autowired
    private PetDao petDao;
    
    @Test
    void testFindByPetId() {
        Iterable<PetFood> foods = petFoodDao.findByPetId(1);
        assertEquals(IterableUtils.size(foods), 4);
    }
    
    @Test
    void testFindByPet() {
        Pet pet = petDao.findById(1).orElseThrow();
        assertNotNull(pet);
        Iterable<PetFood> foods = petFoodDao.findByPet(pet);
        assertEquals(IterableUtils.size(foods), 4);
    }
    
    @Test
    void testFindById() {
        PetFood food = petFoodDao.findById(10).orElseThrow();
        assertNotNull(food);
        assertEquals(food.getId(), Integer.valueOf(10));
    }
    
    @Test
    void testSave() {
        Food foodCarrot = foodDao.findById(FoodId.CARROT).orElseThrow();
        PetFood petFood = new PetFood();
        Pet pet = petDao.getReferenceById(1);
        petFood.setFood(foodCarrot);
        petFood.setFoodCount(100);
        petFood.setPet(pet);
        petFoodDao.save(petFood);
        assertNotNull(petFood.getId());
    }
    
    @Test
    void testFindByPetIdAndFoodType() {
        Optional<PetFood> food = petFoodDao.findByPetIdAndFoodType(
                1, FoodId.DRY_FOOD);
        assertThat(food).isPresent();
    }
    
    @Test
    void testFindByPetIdAndFoodType2() {
        Optional<PetFood> food = petFoodDao.findByPetIdAndFoodType(
                13463456, FoodId.CHOCOLATE);
        assertThat(food).isEmpty();
    }
}
