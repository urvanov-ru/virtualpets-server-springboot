package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Book;

@Transactional(readOnly = true)
public interface BookDao extends JpaRepository<Book, String> {
    List<Book> findByOrderByBookcaseLevelAscBookcaseOrderAsc();
}
