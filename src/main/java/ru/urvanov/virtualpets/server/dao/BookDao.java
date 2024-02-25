package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Book;

@Transactional(readOnly = true)
public interface BookDao extends CrudRepository<Book, Integer> {
}
