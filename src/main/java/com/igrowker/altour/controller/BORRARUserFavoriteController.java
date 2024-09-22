package com.igrowker.altour.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igrowker.altour.persistence.entity.UserFavorite;
import com.igrowker.altour.service.IUserFavoriteService;

@RestController
@RequestMapping("borrar/users/favorites/")
public class BORRARUserFavoriteController {

	@Autowired
	private IUserFavoriteService favoriteService;

	@PostMapping()
	public ResponseEntity<String> addFavorite(@RequestParam String username, @RequestParam Long venueId) {
		try {
			favoriteService.addFavorite(username, venueId);
			return ResponseEntity.ok("Favorite added successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding favorite");
		}
	}

	@GetMapping()
	public ResponseEntity<List<UserFavorite>> getFavorites(@RequestBody String username) {
		try {
			List<UserFavorite> favorites = favoriteService.getFavoritesByUsername(username);
			return ResponseEntity.ok(favorites);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
		}
	}
}
