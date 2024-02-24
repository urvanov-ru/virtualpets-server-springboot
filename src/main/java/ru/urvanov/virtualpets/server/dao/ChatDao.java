/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Chat;

/**
 * @author fedya
 *
 */
@Transactional(readOnly = true)
public interface ChatDao extends CrudRepository<Chat, Integer> {
    Iterable<Chat> findLast(Integer count, Integer userId);
    Iterable<Chat> findFromId(Integer fromId, Integer userId);
}
