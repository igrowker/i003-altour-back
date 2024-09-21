package com.igrowker.altour.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.igrowker.altour.service.ICustomUserService;

@RestController
@RequestMapping("/api/v1")
public class UserSettingsController {
	
	//Creo los endpoints separados de Preferences para que se pueda modificar estas settings de manera independiente a las preferencias de lugares concretos

	@Autowired
	private ICustomUserService customUserService;

	@PostMapping("/set/maxDistance")
	public ResponseEntity<String> setMaxDistance(@RequestParam String username, @RequestParam Integer maxDistance) {

		try {
			customUserService.setMaxDistance(username, maxDistance);
			return ResponseEntity.ok("Max distance set successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error setting max distance: " + e.getMessage());
		}
	}

	@PostMapping("/set/crowdLevel")
	public ResponseEntity<String> setCrowdLevel(@RequestParam String username, @RequestParam Integer crowdLevel) {

		try {
			customUserService.setCrowdLevel(username, crowdLevel);
			return ResponseEntity.ok("Crowd level set successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error setting crowd level: " + e.getMessage());
		}
	}
}
