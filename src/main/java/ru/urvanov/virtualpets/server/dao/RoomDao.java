package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Room;

@Transactional(readOnly = true)
public interface RoomDao extends ListCrudRepository<Room, Integer> {
    Optional<Room> findByPetId(Integer petId);
}
