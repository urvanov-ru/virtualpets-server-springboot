/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialType;

/**
 * @author fedya
 *
 */
@Transactional(readOnly = true)
public interface BuildingMaterialDao extends CrudRepository<BuildingMaterial, Integer> {
    Optional<BuildingMaterial> findByCode(BuildingMaterialType code);
}
