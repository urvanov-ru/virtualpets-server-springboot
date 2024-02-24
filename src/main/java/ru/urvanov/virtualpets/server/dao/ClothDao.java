/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.urvanov.virtualpets.server.dao.domain.Cloth;

/**
 * @author fedya
 *
 */
public interface ClothDao extends JpaRepository<Cloth, Integer> {
}
