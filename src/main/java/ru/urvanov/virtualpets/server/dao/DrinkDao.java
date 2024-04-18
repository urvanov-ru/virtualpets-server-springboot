package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;

@Transactional(readOnly = true)
public interface DrinkDao extends JpaRepository<Drink, DrinkId> {
    @Query(name = "Drink.findAllOrderByMachineWithDrinksLevelAndMachineWithDrinksOrder", nativeQuery = true)
    Iterable<Drink> findAllOrderByMachineWithDrinksLevelAscMachineWithDrinksOrderAsc();
}
