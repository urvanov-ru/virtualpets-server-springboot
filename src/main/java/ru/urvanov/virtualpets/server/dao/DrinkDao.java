package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkType;

@Transactional(readOnly = true)
public interface DrinkDao extends JpaRepository<Drink, DrinkType> {
    Iterable<Drink> findByOrderByMachineWithDrinksLevelAscMachineWithDrinksOrderAsc();
}
