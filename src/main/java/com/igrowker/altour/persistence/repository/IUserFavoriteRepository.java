package com.igrowker.altour.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igrowker.altour.persistence.entity.UserFavorite;

@Repository
public interface IUserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
	List<UserFavorite> findByUser_Username(String username);
}