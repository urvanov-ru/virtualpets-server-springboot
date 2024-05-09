package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Room;

@Transactional(readOnly = true)
public interface RoomDao extends CrudRepository<Room, Integer> {
    Optional<Room> findByPetId(Integer petId);
}
