package ru.urvanov.virtualpets.server.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Chat;

@Transactional(readOnly = true)
public interface ChatDao extends ListCrudRepository<Chat, Integer> {
    List<Chat> findLast(Integer count, Integer userId);
    List<Chat> findFromId(Integer fromId, Integer userId);
}
