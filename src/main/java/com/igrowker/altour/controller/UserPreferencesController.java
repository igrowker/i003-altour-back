package com.igrowker.altour.controller;

import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igrowker.altour.service.IUserPreferenceService;

@RestController
@RequestMapping("preferences")
public class UserPreferencesController {
	
	//Para añadir preferences quizás que el user las añada tipo etiquetas manualmente con sus nombres y se muestren de esa manera o crear una lista con 
	// las que ya vienen determinadas por la api .

	@Autowired
	private IUserPreferenceService userPreferenceService;

	@PostMapping("/add")
	public ResponseEntity<String> addPreference(@RequestParam String username, @RequestParam String preference) {
		try {
			userPreferenceService.addPreference(username, preference);
			return ResponseEntity.ok("Preference added successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error adding preference: " + e.getMessage());
		}
	}

	@DeleteMapping("/remove")
	public ResponseEntity<String> removePreference(@RequestParam String username, @RequestParam String preference) {
		try {
			userPreferenceService.removePreference(username, preference);
			return ResponseEntity.ok("Preference removed successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error removing preference: " + e.getMessage());
		}
	}

	@GetMapping("/list")
	public ResponseEntity<Set<String>> getPreferences(@RequestParam String username) {
		try {
			Set<String> preferences = userPreferenceService.getPreferences(username);
			return ResponseEntity.ok(preferences);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptySet());
		}
	}

}
