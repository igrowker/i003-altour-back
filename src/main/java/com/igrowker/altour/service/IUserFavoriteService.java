package com.igrowker.altour.service;

import java.util.List;

import com.igrowker.altour.persistence.entity.UserFavorite;

public interface IUserFavoriteService {

	void addFavorite(String username, Long venueId);

	void deleteFavorite(UserFavorite userFavorite);

	List<UserFavorite> getFavoritesByUsername(String username);

}
