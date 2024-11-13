package ru.urvanov.virtualpets.server.service;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import ru.urvanov.virtualpets.server.controller.api.domain.RefreshUsersOnlineResult;
import ru.urvanov.virtualpets.server.controller.api.domain.UserInfo;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Listeners(MockitoTestNGListener.class)
public class UserServiceTestNgTest {

    private static final Integer USER_ID = 1;

    private static final Integer PET_ID = 2;

    private static final String USER_FULL_NAME = "Иванов Иван";

    @Mock
    private UserDao userDao;
    
    private ZoneId zoneId = ZoneId.of("Europe/Moscow");
    
    @Spy
    private Clock clock = Clock.fixed(ZonedDateTime
            .of(LocalDate.of(2024, 8, 14), LocalTime.of(16, 58), zoneId)
            .toInstant(), zoneId);

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUsersOnline() throws ServiceException {
        // Подготовка тестовых данных
        UserPetDetails userPetDetails = new UserPetDetails(USER_ID,
                PET_ID);
        List<User> usersOnline = new ArrayList<>();
        User user1 = new User();
        user1.setId(USER_ID);
        user1.setName(USER_FULL_NAME);
        usersOnline.add(user1);
        
        // Настройка mock-объектов
        OffsetDateTime activeAfterDate = OffsetDateTime.now(clock)
                .minusMinutes(5);
        when(userDao.findByActiveDateAfter(eq(activeAfterDate)))
                        .thenReturn(usersOnline);
        
        // Вызов тестируемого метода
        RefreshUsersOnlineResult actual = userService
                .getUsersOnline(userPetDetails);
        
        // Проверка результата
        assertNotNull(actual.users());
        assertEquals(actual.users().size(), 1);
        UserInfo actualUser0 = actual.users().get(0);
        assertEquals((Integer) actualUser0.id(), (Integer) USER_ID);
        assertEquals(actualUser0.name(), USER_FULL_NAME);
    }

}
