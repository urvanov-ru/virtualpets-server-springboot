/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.JournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;

/**
 * @author fedya
 *
 */
public class JournalDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private JournalEntryDao journalEntryDao;


    @Test
    public void testFind1() {
        Optional<JournalEntry> journalEntry = journalEntryDao.findById(1);
        assertThat(journalEntry).isPresent();
    }
    
    
    @Test
    public void testFind2() {
        Optional<JournalEntry> journalEntry = journalEntryDao.findByCode(JournalEntryType.WELCOME);
        assertThat(journalEntry).isPresent();
    }
    
    
    @Test
    public void testFind3() {
        JournalEntry journalEntry = journalEntryDao.getReferenceById(1);
        assertNotNull(journalEntry);
    }
    

}
