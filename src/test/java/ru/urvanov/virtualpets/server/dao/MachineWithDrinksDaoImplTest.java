/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class MachineWithDrinksDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private MachineWithDrinksDao machineWithDrinksDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    public void testFind1() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findById(1);
        assertThat(drink).isPresent();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    public void testFind2() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findFullById(1);
        
        assertThat(drink).map(MachineWithDrinks::getMachineWithDrinksCosts).isPresent();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    public void testFind3() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findById(-1);
        assertThat(drink).isEmpty();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    public void testFind4() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findFullById(-1);
        assertThat(drink).isEmpty();
    }
}
