/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ru.urvanov.virtualpets.server.dao.domain.Room;

/**
 * @author fedya
 *
 */
public interface RoomDao extends CrudRepository<Room, Integer> {
    Optional<Room> findByPetId(Integer petId);
}
