/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 * 
 */
public class UserDaoImplTest extends AbstractDaoImplTest {

    @Autowired
    private UserDao userDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/UserServiceImplTest.xls")
    @Test
    public void findByName() throws Exception {
        Optional<User> user = userDao.findByLogin("Clarence");
        assertThat(user).isPresent();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/UserServiceImplTest.xls")
    @Test
    public void findLastRegisteredUsers() throws Exception {
        Iterable<User> users = userDao.findLastRegisteredUsers(0, 999999);
        assertEquals(IterableUtils.size(users), 1);
    }

}
