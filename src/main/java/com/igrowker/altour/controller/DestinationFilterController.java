package com.igrowker.altour.controller;


import java.util.List;

import com.igrowker.altour.dtos.external.Item;
import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueResponse;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.igrowker.altour.service.DestinationFilterServiceImpl;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping()
public class DestinationFilterController {

	@Autowired
	private DestinationFilterServiceImpl destinationFilterService;

	@Autowired
	private ICustomUserRepository customUserRepository;

	@Value("${here_maps.api.key}")
	private String hereMapsApiKey;

	@Value("${best_time.api.key}")
	private String bestTimeApiKey;

	@Value("${best_time.api.pub.key}")
	private String bestTimeApiPubKey;

	@GetMapping("/destines/filter")
	public Mono<List<Item>> filter(@RequestParam Double lat,
								   @RequestParam Double lng,
								   @RequestParam Integer rad,
								   @RequestParam String activity) {

		return destinationFilterService.getDestinations(lat, lng, rad, activity, hereMapsApiKey);
	}

	@GetMapping("/destines/preferences/filter-besttime")
	public Mono<List<Venue>> filterBestTime(@RequestParam Double lat, @RequestParam Double lng,
			@RequestParam String username, @RequestParam String preference) {

		return Mono.justOrEmpty(customUserRepository.findByUsername(username)).flatMap(user -> {

			Integer maxCrowdLevel = user.getPreferredCrowdLevel();
			Integer maxDistance = user.getMaxSearchDistance();

			return destinationFilterService.getFilteredVenues(lat, lng, maxDistance, preference, maxCrowdLevel,
					bestTimeApiKey);
		}).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")));
	}


	@GetMapping("/destines/filter/byId")
	public Mono<VenueResponse> getDestinationById(@RequestParam String id) {
	    return destinationFilterService.getVenueById(id, bestTimeApiPubKey);
	}

}
