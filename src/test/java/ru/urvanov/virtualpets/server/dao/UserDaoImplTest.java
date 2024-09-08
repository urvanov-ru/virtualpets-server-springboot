package ru.urvanov.virtualpets.server.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.User;

@Sql({ "/ru/urvanov/virtualpets/server/clean.sql",
        "UserDaoImplTest.sql" })
public class UserDaoImplTest extends BaseDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findByName() throws Exception {
        Optional<User> user = userDao.findByLogin("Clarence");
        assertThat(user).isPresent();
    }
    
    @Test
    public void findLastRegisteredUsers() throws Exception {
        Iterable<User> users = userDao.findLastRegisteredUsers(0, 999999);
        assertEquals(IterableUtils.size(users), 1);
    }

}
