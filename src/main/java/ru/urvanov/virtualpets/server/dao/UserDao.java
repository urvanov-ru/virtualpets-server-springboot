package ru.urvanov.virtualpets.server.dao;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.User;

@Transactional(readOnly = true)
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);

    List<User> findByActiveDateAfter(OffsetDateTime offsetDateTime);

    Optional<User> findByLoginAndEmail(String name, String email);

    Optional<User> findByRecoverPasswordKey(String recoverKey);

    default Page<User> findLastRegisteredUsers(int page, int pageSize) {
        return this.findAll(PageRequest.of(page, pageSize,
                Sort.by("registrationDate").descending()));
    }

}
