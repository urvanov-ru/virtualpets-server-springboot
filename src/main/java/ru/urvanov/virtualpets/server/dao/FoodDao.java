/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Food;
import ru.urvanov.virtualpets.server.dao.domain.FoodType;

/**
 * @author fedya
 *
 */
@Transactional(readOnly = true)
public interface FoodDao extends CrudRepository<Food, Integer> {
    Optional<Food> findByCode(FoodType code);
}
