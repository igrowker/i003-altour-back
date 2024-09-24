package com.igrowker.altour.controller;


import java.util.List;
import java.util.Optional;

import com.igrowker.altour.dtos.external.Item;
import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueResponse;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;

import com.igrowker.altour.service.impl.DestineInformationHereMapsHereMapsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.igrowker.altour.service.impl.DestineBestTimeServiceImpl;


@RestController
@RequestMapping("destines")
@Tag(name = "DESTINOS")
public class DestinationFilterController {
	@Autowired
	private DestineInformationHereMapsHereMapsServiceImpl hereMapsDestineService;

	@Autowired
	private DestineBestTimeServiceImpl bestTimedestineService;

	@Autowired
	private ICustomUserRepository customUserRepository;

	@Value("${here_maps.api.key}")
	private String hereMapsApiKey;

	@Value("${best_time.api.key}")
	private String bestTimeApiKey;

	@Value("${best_time.api.pub.key}")
	private String bestTimeApiPubKey;
/*
TODO ESTO LO DEJO COMENTADO HASTA VERIFICAR SI LO USAREMOS O NO...
	@GetMapping("/filter")
	public Mono<List<Item>> filter(@RequestParam Double lat,
								   @RequestParam Double lng,
								   @RequestParam Integer rad,
								   @RequestParam String activity) {
		return destinationFilterService.getDestinations(lat, lng, rad, activity, hereMapsApiKey);
	}
 */

	@GetMapping("/")
	public List<Venue> filterBestTime(@RequestParam Double lat, @RequestParam Double lng,
			@RequestParam(required = false) String preference, Authentication authentication) {

		CustomUser userDetails = (CustomUser) authentication.getPrincipal();
		Optional<CustomUser> userOptional = customUserRepository.findByEmail(userDetails.getUsername());

		if (userOptional.isPresent()) {
			CustomUser user = userOptional.get();
			Integer maxCrowdLevel = user.getPreferredCrowdLevel();
			Integer maxDistance = user.getMaxSearchDistance();

			List<Venue> venues = bestTimedestineService.getFilteredVenues(lat, lng, maxDistance, preference,
					maxCrowdLevel, bestTimeApiKey);

			return venues;

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
	}

	// TODO reciobir un id y segun el tipo llamar a here o besttime
	// TODO reciobir un id y segun el tipo llamar a here o besttime
	// TODO reciobir un id y segun el tipo llamar a here o besttime
	// TODO reciobir un id y segun el tipo llamar a here o besttime
	@GetMapping("/{placeId}")
	public VenueResponse getDestinationInfo(@PathVariable String placeId) {
	    // TODO: Recibir un ID y, según el tipo de servicio, llamar a HERE o BestTime

	   //Manejar la lógica quizas para llamar a here maps api o besttime tratar de filtrar y dar información buena solo tipo historia horarios precio etc
	    // Por ahora, estamos llamando directamente a la BestTime API
	    return bestTimedestineService.getVenueById(placeId, bestTimeApiPubKey);
	}
}
