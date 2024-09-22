package com.igrowker.altour.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("borrar/users/")
public class BORRARUserSettingsController {
	// todo deberiamos modificar los campos crowdLevel y maxDistance en el UserController, en el metodo PUT!
	// todo deberiamos modificar los campos crowdLevel y maxDistance en el UserController, en el metodo PUT!
	// todo deberiamos modificar los campos crowdLevel y maxDistance en el UserController, en el metodo PUT!
	// todo deberiamos modificar los campos crowdLevel y maxDistance en el UserController, en el metodo PUT!
	// todo deberiamos modificar los campos crowdLevel y maxDistance en el UserController, en el metodo PUT!

	/*
	
	//Creo los endpoints separados de Preferences para que se pueda modificar estas settings de manera independiente a las preferencias de lugares concretos

	@Autowired
	private ICustomUserService customUserService;


	@PutMapping("maxDistance")
	public ResponseEntity<String> setMaxDistance(@RequestParam String username, @RequestParam Integer maxDistance) {

		try {
			customUserService.setMaxDistance(username, maxDistance);
			return ResponseEntity.ok("Max distance set successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error setting max distance: " + e.getMessage());
		}
	}

	@PutMapping("crowdLevel")
	public ResponseEntity<String> setCrowdLevel(@RequestParam String username, @RequestParam Integer crowdLevel) {

		try {
			customUserService.setCrowdLevel(username, crowdLevel);
			return ResponseEntity.ok("Crowd level set successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error setting crowd level: " + e.getMessage());
		}
	}

	 */
}
