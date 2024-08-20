package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks_;

@Transactional(readOnly = true)
public interface MachineWithDrinksDao
        extends ListCrudRepository<MachineWithDrinks, Integer>,
        JpaSpecificationExecutor<MachineWithDrinks> {
    default Optional<MachineWithDrinks> findFullById(Integer id) {
        return this.findOne(
                MachineWithDrinksDao.findFullByIdSpecification(id));
    }

    private static Specification<MachineWithDrinks> findFullByIdSpecification(
            Integer id) {
        return (root, query, builder) -> {
            root.fetch(MachineWithDrinks_.machineWithDrinksCosts,
                    JoinType.LEFT);
            Predicate predicate = builder
                    .equal(root.get(MachineWithDrinks_.id), id);
            return predicate;
        };
    }

    default List<MachineWithDrinks> findAllFull() {
        return this.findAll(
                MachineWithDrinksDao.findAllFullSpecification());
    }

    private static Specification<MachineWithDrinks> findAllFullSpecification() {
        return (root, query, builder) -> {
            root.fetch(MachineWithDrinks_.machineWithDrinksCosts,
                    JoinType.LEFT);
            return builder.and();
        };
    }
}
