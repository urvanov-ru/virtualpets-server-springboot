package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.urvanov.virtualpets.server.dao.domain.Level;

public interface LevelDao extends JpaRepository<Level, Integer> {
}
