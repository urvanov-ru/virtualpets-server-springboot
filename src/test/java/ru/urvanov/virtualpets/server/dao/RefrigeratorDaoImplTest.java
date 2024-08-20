/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class RefrigeratorDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private RefrigeratorDao refrigeratorDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind1() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findById(1);
        assertThat(refrigerator).isPresent();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind2() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findFullById(2);
        assertThat(refrigerator).map(Refrigerator::getRefrigeratorCosts).isPresent();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind3() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findById(-1);
        assertThat(refrigerator).isEmpty();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind4() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findFullById(-1);
        assertThat(refrigerator).isEmpty();
    }
}
