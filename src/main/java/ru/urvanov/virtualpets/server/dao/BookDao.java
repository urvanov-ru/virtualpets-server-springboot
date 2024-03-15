package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Book;

@Transactional(readOnly = true)
public interface BookDao extends JpaRepository<Book, String> {

    Iterable<Book> findByOrderByBookcaseLevelAscBookcaseOrderAsc();
}
