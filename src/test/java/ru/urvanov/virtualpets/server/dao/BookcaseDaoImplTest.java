package ru.urvanov.virtualpets.server.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.Bookcase;

@Sql("/ru/urvanov/virtualpets/server/clean.sql")
class BookcaseDaoImplTest extends BaseDaoImplTest {
    
    @Autowired
    BookcaseDao bookcaseDao;

    @Test
    void testFind1() {
        Optional<Bookcase> bookcase = bookcaseDao.findById(1);
        assertThat(bookcase).isPresent();
    }
    
    @Test
    void testFind2() {
        Optional<Bookcase> bookcase = bookcaseDao.findFullById(1);
        assertThat(bookcase).isPresent();
        assertThat(bookcase).map(Bookcase::getBookcaseCosts).isPresent();
    }
    
    @Test
    void testFind3() {
        Optional<Bookcase> bookcase = bookcaseDao.findById(-1);
        assertThat(bookcase).isEmpty();
    }
    
    @Test
    void testFind4() {
        Optional<Bookcase> bookcase = bookcaseDao.findFullById(-1);
        assertThat(bookcase).isEmpty();
    }
}
