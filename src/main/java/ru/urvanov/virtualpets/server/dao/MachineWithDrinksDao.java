/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;

/**
 * @author fedya
 *
 */
@Transactional(readOnly = true)
public interface MachineWithDrinksDao extends CrudRepository<MachineWithDrinks, Integer> {
    Optional<MachineWithDrinks> findFullById(Integer id);
}
