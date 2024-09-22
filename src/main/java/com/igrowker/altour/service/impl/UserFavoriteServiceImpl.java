package com.igrowker.altour.service.impl;

import java.util.List;

import com.igrowker.altour.service.IUserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.UserFavorite;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;
import com.igrowker.altour.persistence.repository.IUserFavoriteRepository;

@Service
public class UserFavoriteServiceImpl implements IUserFavoriteService {

	@Autowired
	private IUserFavoriteRepository favoriteRepository;

	@Autowired
	private ICustomUserRepository userRepository;

	@Override
	@Transactional
	public void addFavorite(String username, Long venueId) {
		CustomUser user = userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		UserFavorite favorite = new UserFavorite();
		favorite.setUser(user);
		favorite.setDestinationId(venueId);
		favoriteRepository.save(favorite);
	}

	@Override
	@Transactional
	public void deleteFavorite(UserFavorite userFavorite) {
		favoriteRepository.delete(userFavorite);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserFavorite> getFavoritesByUsername(String username) {
		return favoriteRepository.findByUser_Username(username);
	}
}
