package ru.urvanov.virtualpets.server.service;

import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.domain.UserProfile;

public interface UserService {

    UserProfile getProfile();

    Iterable<User> findLastRegisteredUsers(int start, int limit);

    Optional<User> findByRecoverPasswordKey(String recoverPasswordKey);

}
