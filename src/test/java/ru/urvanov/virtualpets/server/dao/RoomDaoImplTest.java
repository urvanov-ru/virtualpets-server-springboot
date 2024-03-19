/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.Room;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class RoomDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private PetDao petDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RoomServiceImplTest.xls")
    @Test
    public void testFind1() {
        Optional<Room> room = roomDao.findByPetId(1);
        assertThat(room).map(Room::getPetId).isPresent();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RoomServiceImplTest.xls")
    @Test
    public void testFind2() {
        Optional<Room> room = roomDao.findByPetId(-1);
        assertThat(room).isEmpty();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RoomServiceImplTest.xls")
    @Test
    public void testSaveNew() {
        Room room = new Room();
        Pet pet = petDao.getReferenceById(2);
        room.setPetId(pet.getId());
        room.setBoxNewbie1(true);
        room.setBoxNewbie2(true);
        room.setBoxNewbie3(true);
        roomDao.save(room);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RoomServiceImplTest.xls")
    @Test
    public void testSaveExist() {
        Room room = roomDao.findByPetId(1).orElseThrow();
        room.setBoxNewbie1(false);
        roomDao.save(room);
        room = roomDao.findByPetId(1).orElseThrow();
        assertEquals(room.isBoxNewbie1(), false);
    }

}
