package com.igrowker.altour.controller;

import java.util.Set;

import com.igrowker.altour.persistence.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igrowker.altour.service.IUserPreferenceService;

@RestController
@RequestMapping("borrar/users/preferences/")
public class BORRARUserPreferencesController {
	
	//Para añadir preferences quizás que el user las añada tipo etiquetas manualmente con sus nombres y se muestren de esa manera o crear una lista con 
	// las que ya vienen determinadas por la api .

	@Autowired
	private IUserPreferenceService userPreferenceService;

	@PostMapping()
	public ResponseEntity<String> addPreference(@RequestParam String preference, Authentication authentication) {//TODO Spring inyecta el obj authentication en todos los endpoints protegidos, y se puede acceder a los detalles del usuario
		CustomUser userDetails = (CustomUser) authentication.getPrincipal();
		return new ResponseEntity<>(userPreferenceService.addPreference(userDetails.getUsername(), preference) , HttpStatus.CREATED);
	}

	@DeleteMapping()
	public ResponseEntity<String> removePreference(@RequestParam String preference, Authentication authentication) {//TODO Spring inyecta el obj authentication en todos los endpoints protegidos, y se puede acceder a los detalles del usuario
		CustomUser userDetails = (CustomUser) authentication.getPrincipal();
		return new ResponseEntity<>(userPreferenceService.removePreference(userDetails.getUsername(), preference), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<Set<String>> getPreferences(Authentication authentication) {//TODO Spring inyecta el obj authentication en todos los endpoints protegidos, y se puede acceder a los detalles del usuario
		CustomUser userDetails = (CustomUser) authentication.getPrincipal();
		return new ResponseEntity<>(userPreferenceService.getPreferencesByEmail(userDetails.getUsername()) , HttpStatus.OK);
	}

}
