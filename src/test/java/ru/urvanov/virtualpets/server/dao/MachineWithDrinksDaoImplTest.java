package ru.urvanov.virtualpets.server.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;

@Sql("/ru/urvanov/virtualpets/server/clean.sql")
public class MachineWithDrinksDaoImplTest extends BaseDaoImplTest {
    
    @Autowired
    private MachineWithDrinksDao machineWithDrinksDao;

    @Test
    public void testFind1() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findById(1);
        assertThat(drink).isPresent();
    }
    
    @Test
    public void testFind2() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findFullById(1);
        
        assertThat(drink).map(MachineWithDrinks::getMachineWithDrinksCosts).isPresent();
    }
    
    @Test
    public void testFind3() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findById(-1);
        assertThat(drink).isEmpty();
    }
    
    @Test
    public void testFind4() {
        Optional<MachineWithDrinks> drink = machineWithDrinksDao.findFullById(-1);
        assertThat(drink).isEmpty();
    }
}
