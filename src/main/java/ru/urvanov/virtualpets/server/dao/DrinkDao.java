package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Drink;
import ru.urvanov.virtualpets.server.dao.domain.DrinkType;
import ru.urvanov.virtualpets.server.dao.domain.Room;

@Transactional(readOnly = true)
public interface DrinkDao extends CrudRepository<Drink, Integer> {
    Optional<Drink> findByDrinkType(DrinkType code);
}
