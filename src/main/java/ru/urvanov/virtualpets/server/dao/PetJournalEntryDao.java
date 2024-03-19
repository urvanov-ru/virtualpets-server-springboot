package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;

@Transactional(readOnly = true)
public interface PetJournalEntryDao extends CrudRepository<PetJournalEntry, Integer>{
    
    List<PetJournalEntry> findAllByPetId(Integer petId, Pageable pageable);
    
    default Iterable<PetJournalEntry> findLastByPetId(Integer petId, Integer count) {
        return this.findAllByPetId(petId, PageRequest.of(0, count, Sort.by("createdAt").descending()));
    }

    Optional<PetJournalEntry> findByPetIdAndJournalEntry(Integer petId,
            JournalEntryId code);


}
