package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;

@Transactional(readOnly = true)
public interface FoodDao extends JpaRepository<Food, FoodId> {
    List<Food> findByOrderByRefrigeratorLevelAscRefrigeratorOrderAsc();
}
