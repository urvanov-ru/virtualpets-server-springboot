package ru.urvanov.virtualpets.server.dao;

import ru.urvanov.virtualpets.server.dao.domain.LastRegisteredUser;

public interface JdbcReportDao {

    Iterable<LastRegisteredUser> findLastRegisteredUsers(int start, int limit);
}
