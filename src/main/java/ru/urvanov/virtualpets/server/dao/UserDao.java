/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.User;
/**
 * @author fedya
 *
 */
@Transactional(readOnly = true)
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
    Optional<User> findByLoginAndPassword(String login, String password);
    Iterable<User> findOnline();
    Optional<User> findByLoginAndEmail(String name, String email);
    Optional<User> findByFacebookKey(String facebookKey);
    Optional<User> findByUnid(String unid);
    Optional<User> findByRecoverPasswordKey(String recoverKey);
    Optional<User> findByVkontakteKey(String vkontakteKey);
    Optional<User> findByTwitterKey(String twitterKey);
    
    
    default Iterable<User> findLastRegisteredUsers(int page, int pageSize) {
        return this.findAll(PageRequest.of(page, pageSize, Sort.by("registrationDate").descending()));
    }
    
    
}
