package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator_;

@Transactional(readOnly = true)
public interface RefrigeratorDao
        extends CrudRepository<Refrigerator, Integer>,
        JpaSpecificationExecutor<Refrigerator> {
    default Optional<Refrigerator> findFullById(Integer id) {
        return this
                .findOne(RefrigeratorDao.findFullByIdSpecification(id));
    }

    private static Specification<Refrigerator> findFullByIdSpecification(
            Integer id) {
        return (root, query, builder) -> {
            root.fetch(Refrigerator_.refrigeratorCosts);
            Predicate predicate = builder
                    .equal(root.get(Refrigerator_.id), id);
            return predicate;
        };
    }

    default List<Refrigerator> findAllFull() {
        return this.findAll(RefrigeratorDao.findAllFullSpecification());
    }

    private static Specification<Refrigerator> findAllFullSpecification() {
        return (root, query, builder) -> {
            root.fetch(Refrigerator_.refrigeratorCosts);
            return builder.and();
        };
    }
}
