package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;

@Transactional(readOnly = true)
public interface MachineWithDrinksDao extends ListCrudRepository<MachineWithDrinks, Integer> {
    Optional<MachineWithDrinks> findFullById(Integer id);
}
