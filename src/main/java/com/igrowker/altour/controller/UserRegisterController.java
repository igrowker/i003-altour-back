package com.igrowker.altour.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.RegisterCustomUserDTO;
import com.igrowker.altour.service.ICustomUserService;

@RestController
@RequestMapping("/api/v1")
public class UserRegisterController {

	@Autowired
	private ICustomUserService customUserService;

	@PostMapping("/registerUser")
	public ResponseEntity<String> register(@RequestBody RegisterCustomUserDTO registerUserDTO) {
		CustomUser newUser = new CustomUser();
		newUser.setUsername(registerUserDTO.getUsername());
		newUser.setPassword(registerUserDTO.getPassword()); // Asegúrate de encriptar la contraseña
		newUser.setEmail(registerUserDTO.getEmail());

		  // Establecer valores predeterminados para maxSearchDistance y preferredCrowdLevel si no se proporcionan
	    newUser.setMaxSearchDistance(
	        registerUserDTO.getMaxSearchDistance() != null ? registerUserDTO.getMaxSearchDistance() : 4000 // Valor predeterminado como Integer
	    );
	    newUser.setPreferredCrowdLevel(
	        registerUserDTO.getPreferredCrowdLevel() != null ? registerUserDTO.getPreferredCrowdLevel() : 70 // Valor predeterminado como Integer
	    );

		// Guardar el nuevo usuario
		customUserService.saveUser(newUser);

		return ResponseEntity.ok("Usuario registrado con éxito");
	}
}
