package ru.urvanov.virtualpets.server.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.Room;

@Sql({ "/ru/urvanov/virtualpets/server/clean.sql",
        "RoomDaoImplTest.sql" })
class RoomDaoImplTest extends BaseDaoImplTest {
    
    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private PetDao petDao;

    @Test
    void testFind1() {
        Optional<Room> room = roomDao.findByPetId(1);
        assertThat(room).map(Room::getPetId).isPresent();
    }
    
    @Test
    void testFind2() {
        Optional<Room> room = roomDao.findByPetId(-1);
        assertThat(room).isEmpty();
    }
    
    @Test
    void testSaveNew() {
        Room room = new Room();
        Pet pet = petDao.getReferenceById(2);
        room.setPetId(pet.getId());
        room.setBoxNewbie1(true);
        room.setBoxNewbie2(true);
        room.setBoxNewbie3(true);
        roomDao.save(room);
    }
    
    @Test
    void testSaveExist() {
        Room room = roomDao.findByPetId(1).orElseThrow();
        room.setBoxNewbie1(false);
        roomDao.save(room);
        room = roomDao.findByPetId(1).orElseThrow();
        assertEquals(room.isBoxNewbie1(), false);
    }

}
