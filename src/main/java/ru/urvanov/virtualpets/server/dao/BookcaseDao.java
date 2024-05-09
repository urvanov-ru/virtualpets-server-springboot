package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase_;

@Transactional(readOnly = true)
public interface BookcaseDao
        extends ListCrudRepository<Bookcase, Integer>,
        JpaSpecificationExecutor<Bookcase> {

    default Optional<Bookcase> findFullById(Integer id) {
        return this.findOne(BookcaseDao.findFullByIdSpecification(id));
    }

    static Specification<Bookcase> findFullByIdSpecification(
            Integer id) {
        return (root, query, builder) -> {
            Predicate predicateId = builder.equal(root.get(Bookcase_.id),
                    id);
            root.fetch(Bookcase_.bookcaseCost, JoinType.LEFT);
            return predicateId;
        };
    }

    default List<Bookcase> findAllFull() {
        return this.findAll(BookcaseDao.findAllFullSpecification());
    }

    static Specification<Bookcase> findAllFullSpecification() {
        return (root, query, builder) -> {
            root.fetch(Bookcase_.bookcaseCost, JoinType.LEFT);
            return builder.and();
        };
    }
}
