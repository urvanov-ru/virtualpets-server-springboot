package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.Achievement;
import ru.urvanov.virtualpets.server.dao.domain.AchievementCode;

@Transactional(readOnly = true)
public interface AchievementDao extends CrudRepository<Achievement, Integer> {
    Achievement findByCode(AchievementCode code);
}
