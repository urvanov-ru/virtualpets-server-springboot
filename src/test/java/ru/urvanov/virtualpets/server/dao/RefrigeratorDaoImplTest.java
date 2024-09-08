package ru.urvanov.virtualpets.server.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;

@Sql("/ru/urvanov/virtualpets/server/clean.sql")
public class RefrigeratorDaoImplTest extends BaseDaoImplTest {
    
    @Autowired
    private RefrigeratorDao refrigeratorDao;

    @Test
    public void testFind1() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findById(1);
        assertThat(refrigerator).isPresent();
    }
    
    @Test
    public void testFind2() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findFullById(2);
        assertThat(refrigerator).map(Refrigerator::getRefrigeratorCosts).isPresent();
    }
    
    @Test
    public void testFind3() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findById(-1);
        assertThat(refrigerator).isEmpty();
    }
    
    @Test
    public void testFind4() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findFullById(-1);
        assertThat(refrigerator).isEmpty();
    }
}
