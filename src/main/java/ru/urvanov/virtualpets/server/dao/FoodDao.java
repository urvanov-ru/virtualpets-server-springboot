package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;

@Transactional(readOnly = true)
public interface FoodDao extends JpaRepository<Food, FoodType> {
    Iterable<Food> findByOrderByRefrigeratorLevelAscRefrigeratorOrderAsc();
}
