package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.MapJoin;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry_;
import ru.urvanov.virtualpets.server.dao.domain.Pet_;

@Transactional(readOnly = true)
public interface PetDao
        extends JpaRepository<Pet, Integer>, JpaSpecificationExecutor<Pet> {

    Optional<Pet> findFullById(Integer id);

    List<Pet> findByUserId(Integer userId);
    
    long countByUserId(Integer userId);
    
    default long getPetNewJournalEntriesCount(Integer petId) {
        return this.count(PetDao.getPetNewJournalEntriesSpecification(petId));
    }
    
    private static Specification<Pet> getPetNewJournalEntriesSpecification(Integer petId) {
        return (rootPet,
                    criteriaQuery,
                    criteriaBuilder) -> {
                MapJoin<Pet, JournalEntryId, PetJournalEntry> joinPetJournalEntries = rootPet
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

    default Page<Pet> findLastCreatedPets(int page, int pageSize) {
        return this.findAll(PageRequest.of(page, pageSize, Sort.by("createdDate").descending()));
    }
    
    @Query("from Pet p left outer join p.buildingMaterials bm where p.id = ?1")
    Optional<Pet> findByIdWithBuildingMaterials(Integer id);
    
    @EntityGraph("pet.buildingMaterials")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithFullBuildingMaterials(Integer id);
    
    @EntityGraph("pet.foods")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithFullFoods(Integer id);
    
    @EntityGraph("pet.drinks")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithFullDrinks(Integer id);
    
    @EntityGraph("pet.cloths")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithFullCloths(Integer id);
    
    @EntityGraph("pet.books")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithFullBooks(Integer id);
    
    @EntityGraph("pet.journalEntriesAndAchievements")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithJournalEntriesAndAchievements(Integer id);
    
    @EntityGraph("pet.booksAndJournalEntriesAndBuildingMaterials")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithBooksAndJournalEntriesAndBuildingMaterials(Integer id);
    
    @EntityGraph("pet.foodsAndJournalEntriesAndBuildingMaterials")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithFoodsAndJournalEntriesAndBuildingMaterials(Integer id);
    
    @EntityGraph("pet.drinksAndJournalEntriesAndBuildingMaterialsAndAchievements")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithDrinksAndJournalEntriesAndBuildingMaterialsAndAchievements(
            Integer id);
    
    @EntityGraph("pet.drinksAndJournalEntriesAndAchievements")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithDrinksAndJournalEntriesAndAchievements(Integer id);
    
    @EntityGraph("pet.foodsAndJournalEntriesAndAchievements")
    @Query(name = "Pet.findById")
    Optional<Pet> findByIdWithFoodsJournalEntriesAndAchievements(Integer id);
}
