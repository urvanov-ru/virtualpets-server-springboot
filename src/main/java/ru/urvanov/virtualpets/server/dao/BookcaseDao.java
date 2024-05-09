package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Bookcase;

@Transactional(readOnly = true)
public interface BookcaseDao extends CrudRepository<Bookcase, Integer> {
    Optional<Bookcase> findFullById(Integer id);
}
