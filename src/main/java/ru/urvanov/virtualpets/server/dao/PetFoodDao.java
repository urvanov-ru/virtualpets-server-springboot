package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import ru.urvanov.virtualpets.server.dao.domain.FoodId;
import ru.urvanov.virtualpets.server.dao.domain.Food_;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.PetFood_;
import ru.urvanov.virtualpets.server.dao.domain.Pet_;

@Transactional(readOnly = true)
public interface PetFoodDao extends ListCrudRepository<PetFood, Integer>, JpaSpecificationExecutor<PetFood> {
    List<PetFood> findByPetId(Integer petId);
    List<PetFood> findByPet(Pet pet);
    
    
    default Optional<PetFood> findByPetIdAndFoodType(Integer petId, FoodId foodType) {
        return this.findAll(PetFoodDao.findByPetIdAndFoodTypeSpecification(petId, foodType), PageRequest.of(0, 1)).stream().findFirst();
    }
    static Specification<PetFood> findByPetIdAndFoodTypeSpecification(Integer petId,
            FoodId foodType) {
        return (root, query, builder) -> {
            
            Predicate predicatePetId = builder.equal(root.get(PetFood_.pet).get(Pet_.id), petId);
            Predicate predicateFoodType = builder.equal(root.get(PetFood_.food).get(Food_.id), foodType);
            Predicate predicate = builder.and(predicatePetId, predicateFoodType);
            return predicate;
        };
    }
    
}
