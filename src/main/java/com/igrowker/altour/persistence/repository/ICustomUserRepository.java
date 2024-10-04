package com.igrowker.altour.persistence.repository;

import com.igrowker.altour.persistence.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomUserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    // CustomUser findByEmail(String email);
    Optional<CustomUser> findByUsername(String username);
}
