package ru.urvanov.virtualpets.server.dao;

import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Book;

@Transactional(readOnly = true)
public interface BookDao {
    Book findById(Integer id);
}
