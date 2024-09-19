package com.igrowker.altour.persistence.repository;

import com.igrowker.altour.persistence.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomUserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByEmail(String email);
}
