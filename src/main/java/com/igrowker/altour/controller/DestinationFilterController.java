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
import org.springframework.cache.annotation.CacheEvict;
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
	 * TODO ESTO LO DEJO COMENTADO HASTA VERIFICAR SI LO USAREMOS O NO...
	 * 
	 * @GetMapping("/filter") public Mono<List<Item>> filter(@RequestParam Double
	 * lat,
	 * 
	 * @RequestParam Double lng,
	 * 
	 * @RequestParam Integer rad,
	 * 
	 * @RequestParam String activity) { return
	 * destinationFilterService.getDestinations(lat, lng, rad, activity,
	 * hereMapsApiKey); }
	 */

	@GetMapping("/")
	public List<Venue> filterBestTime(@RequestParam Double lat, @RequestParam Double lng,
			@RequestParam(required = false) String preference, @RequestParam(required = false) Integer maxCrowdLevel,
			@RequestParam(required = false) Integer maxDistance, Authentication authentication) {

		Integer crowdLevel = maxCrowdLevel != null ? maxCrowdLevel : 80;
		Integer distance = maxDistance != null ? maxDistance : 1000;

		if (authentication == null) {
			// todo esta logica sera seguida por un usuario que no esta autenticado.. ya que
			// front muestra mapas para logueados y no logueados
			return bestTimedestineService.getFilteredVenues(lat, lng, distance, preference, crowdLevel, bestTimeApiKey);
		}

		CustomUser userDetails = (CustomUser) authentication.getPrincipal();
		Optional<CustomUser> userOptional = customUserRepository.findByEmail(userDetails.getUsername());
		crowdLevel = userOptional.get().getPreferredCrowdLevel();
		distance = userOptional.get().getMaxSearchDistance();
		return bestTimedestineService.getFilteredVenues(lat, lng, distance, preference, crowdLevel, bestTimeApiKey);
	}

	// TODO reciobir un id y segun el tipo llamar a here o besttime
	// TODO reciobir un id y segun el tipo llamar a here o besttime
	// TODO reciobir un id y segun el tipo llamar a here o besttime
	// TODO reciobir un id y segun el tipo llamar a here o besttime
	@GetMapping("/{placeId}")
	public Venue getDestinationInfo(@PathVariable String placeId) {
		// Obtenemos el venue específico
		VenueResponse venueResponse = bestTimedestineService.getVenueById(placeId, bestTimeApiPubKey);

		if (venueResponse == null || venueResponse.getVenueInfo() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar no encontrado");
		}

		List<Venue> filteredVenues = bestTimedestineService.getFilteredVenuesWithoutCache(
				venueResponse.getVenueInfo().getVenueLat(), venueResponse.getVenueInfo().getVenueLng(), 10, null, 100,
				bestTimeApiKey);  // los parámetros son estáticos 10 el radio para que en el filtro encuentre si o si el place concreto 100 busy max para que no lo salte etc.

		// Filtrar el resultado para obtener el venue que coincide con el placeId
		Venue matchedVenue = filteredVenues.stream().filter(v -> v.getVenueId().equals(placeId)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Lugar no encontrado en los resultados filtrados"));

		return matchedVenue;
	}
}
