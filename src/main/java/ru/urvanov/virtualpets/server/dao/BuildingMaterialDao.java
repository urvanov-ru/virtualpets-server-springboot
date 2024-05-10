package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;

@Transactional(readOnly = true)
public interface BuildingMaterialDao
        extends JpaRepository<BuildingMaterial, BuildingMaterialId> {
}
