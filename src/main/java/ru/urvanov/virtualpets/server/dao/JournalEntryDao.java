/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.JournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;

/**
 * @author fedya
 *
 */
@Transactional(readOnly = true)
public interface JournalEntryDao extends JpaRepository<JournalEntry, Integer> {
    Optional<JournalEntry> findByCode(JournalEntryType code);
}
