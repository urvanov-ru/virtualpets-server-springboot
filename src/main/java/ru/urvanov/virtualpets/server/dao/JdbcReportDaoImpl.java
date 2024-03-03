package ru.urvanov.virtualpets.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.LastRegisteredUser;
import ru.urvanov.virtualpets.server.dao.mapper.LastRegisteredUserMapper;

@Repository
public class JdbcReportDaoImpl implements JdbcReportDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private LastRegisteredUserMapper lastRegisteredUsersMapper = new LastRegisteredUserMapper();

    @Override
    @Transactional(readOnly = true)
    public Iterable<LastRegisteredUser> findLastRegisteredUsers(int start,
            int limit) {
        return jdbcTemplate.query("""
                select
                  u.registration_date as registration_date,
                  u.name as name,
                  count(p.id) as pets_count
                from "user" u
                  left join pet p on p.user_id = u.id
                group by
                  u.registration_date,
                  u.name
                order by registration_date desc offset ? limit ?
                """,
                lastRegisteredUsersMapper,
                start,
                limit);
    }

}
