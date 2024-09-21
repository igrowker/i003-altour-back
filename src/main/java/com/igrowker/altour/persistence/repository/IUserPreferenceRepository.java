package com.igrowker.altour.persistence.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.UserPreference;

@Repository
public interface IUserPreferenceRepository extends JpaRepository<UserPreference, Long> {

	// Encuentra preferencias por el usuario y devuelve un Set
	@Query("SELECT DISTINCT up FROM UserPreference up WHERE up.user = :user")
	Set<UserPreference> findByUser(@Param("user") CustomUser user);

	// Opcional: Eliminar una preferencia específica
	void deleteByUserAndPreference(CustomUser user, String preference);
}
