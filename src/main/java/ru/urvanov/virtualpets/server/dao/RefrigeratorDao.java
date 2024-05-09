package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;

@Transactional(readOnly = true)
public interface RefrigeratorDao extends CrudRepository<Refrigerator, Integer> {
    Optional<Refrigerator> findFullById(Integer id);
}
