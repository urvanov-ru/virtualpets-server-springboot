/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class BookcaseDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    BookcaseDao bookcaseDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind1() {
        Optional<Bookcase> bookcase = bookcaseDao.findById(1);
        assertThat(bookcase).isPresent();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind2() {
        Optional<Bookcase> bookcase = bookcaseDao.findFullById(1);
        assertThat(bookcase).isPresent();
        assertThat(bookcase).map(Bookcase::getBookcaseCosts).isPresent();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind3() {
        Optional<Bookcase> bookcase = bookcaseDao.findById(-1);
        assertThat(bookcase).isEmpty();
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind4() {
        Optional<Bookcase> bookcase = bookcaseDao.findFullById(-1);
        assertThat(bookcase).isEmpty();
    }
}
