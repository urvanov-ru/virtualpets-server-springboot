package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.MapJoin;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry_;
import ru.urvanov.virtualpets.server.dao.domain.Pet_;

@Transactional(readOnly = true)
public interface PetDao
        extends JpaRepository<Pet, Integer>, JpaSpecificationExecutor<Pet> {

    Optional<Pet> findFullById(Integer id);

    Iterable<Pet> findByUserId(Integer userId);
    
    long countByUserId(Integer userId);
    
    default long getPetNewJournalEntriesCount(Integer petId) {
        return this.count(PetDao.getPetNewJournalEntriesSpecification(petId));
    }
    
    private static Specification<Pet> getPetNewJournalEntriesSpecification(Integer petId) {
        return (rootPet,
                    criteriaQuery,
                    criteriaBuilder) -> {
                MapJoin<Pet, JournalEntryType, PetJournalEntry> joinPetJournalEntries = rootPet
                        .join(Pet_.journalEntries, JoinType.LEFT);
                return criteriaBuilder.and(
                        criteriaBuilder
                                .and(criteriaBuilder.equal(
                                        joinPetJournalEntries
                                                .get(PetJournalEntry_.readed),
                                        false)),
                        criteriaBuilder.equal(rootPet.get(Pet_.id), petId));
                    };
    }

    default Iterable<Pet> findLastCreatedPets(int page, int pageSize) {
        return this.findAll(PageRequest.of(page, pageSize, Sort.by("createdDate").descending()));
    }
}
