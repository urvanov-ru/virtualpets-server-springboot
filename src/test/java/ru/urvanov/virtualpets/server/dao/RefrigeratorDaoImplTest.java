package ru.urvanov.virtualpets.server.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;

@Sql("/ru/urvanov/virtualpets/server/clean.sql")
class RefrigeratorDaoImplTest extends BaseDaoImplTest {
    
    @Autowired
    private RefrigeratorDao refrigeratorDao;

    @Test
    void testFind1() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findById(1);
        assertThat(refrigerator).isPresent();
    }
    
    @Test
    void testFind2() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findFullById(2);
        assertThat(refrigerator).map(Refrigerator::getRefrigeratorCosts).isPresent();
    }
    
    @Test
    void testFind3() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findById(-1);
        assertThat(refrigerator).isEmpty();
    }
    
    @Test
    void testFind4() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findFullById(-1);
        assertThat(refrigerator).isEmpty();
    }
}
