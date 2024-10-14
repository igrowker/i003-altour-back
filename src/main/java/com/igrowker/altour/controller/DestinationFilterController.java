package com.igrowker.altour.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueResponse;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;

import com.igrowker.altour.service.IDestineAuditService;
import com.igrowker.altour.service.impl.DestineInformationHereMapsHereMapsServiceImpl;

import io.lettuce.core.RedisConnectionException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
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
	private IDestineAuditService destineAuditService;

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

	@GetMapping("/")
	public List<Venue> filterBestTime(@RequestParam Double lat, @RequestParam Double lng,
			@RequestParam(required = false) String preference, @RequestParam(required = false) Integer maxCrowdLevel,
			@RequestParam(required = false) Integer maxDistance,
			@RequestParam(required = false, defaultValue = "1") Integer busyMin, Authentication authentication) {

		Integer crowdLevel = maxCrowdLevel != null ? maxCrowdLevel : 80;
		Integer distance = maxDistance != null ? maxDistance : 1000;

		if (authentication == null) { // front muestra mapas para logueados y no logueados
			return getFilteredVenues(lat, lng, distance, preference, crowdLevel, busyMin);
		}

		CustomUser userDetails = (CustomUser) authentication.getPrincipal();
		Optional<CustomUser> userOptional = customUserRepository.findByEmail(userDetails.getUsername());
		crowdLevel = userOptional.get().getPreferredCrowdLevel();
		distance = userOptional.get().getMaxSearchDistance();
		return getFilteredVenues(lat, lng, distance, preference, crowdLevel, busyMin);
	}


	@GetMapping("/{placeId}")
	public Venue getDestinationInfo(@PathVariable String placeId) {
		// Obtenemos el venue específico
		VenueResponse venueResponse = bestTimedestineService.getVenueById(placeId, bestTimeApiPubKey);

		if (venueResponse == null || venueResponse.getVenueInfo() == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lugar no encontrado");
		}

		// Se añadio el busy min como parámetro opcional para que aqui sea siempre 0
		// porque ? porque si se busca un lugar que este cerrado por ejemplo
		// Si se busca de noche la afluencia es de 0 cuando esta cerrado entonces no lo
		// encontraria por defecto el busy min es 1 en el otro controller porque
		// Así evitas que en la lista te de sitios cerrados pero aquí es necesario que
		// te lo devuelva este como este abierto o cerrado.
		List<Venue> filteredVenues = bestTimedestineService.getFilteredVenuesWithoutCache(
				venueResponse.getVenueInfo().getVenueLat(), venueResponse.getVenueInfo().getVenueLng(), 10, null, 100,
				0, bestTimeApiKey); // los parámetros son estáticos 10 el radio para que en el filtro encuentre si o
									// si el place concreto 100 busy max para que no lo salte etc.

		// Filtrar el resultado para obtener el venue que coincide con el placeId
		Venue matchedVenue = filteredVenues.stream().filter(v -> v.getVenueId().equals(placeId)).findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Lugar no encontrado en los resultados filtrados"));

		return matchedVenue;
	}

	private List<Venue> getFilteredVenues(Double lat, Double lng, Integer maxDistance, String preference,
			Integer maxCrowdLevel, Integer busyMin) {
		try {
			List<Venue> list = bestTimedestineService.getFilteredVenuesWithCache(lat, lng, maxDistance, preference, maxCrowdLevel,busyMin, bestTimeApiKey);
			destineAuditService.saveDestine(new Date(),lat, lng, maxDistance, preference, maxCrowdLevel, busyMin, true);
			return list;
		} catch (RedisConnectionFailureException | RedisConnectionException | RedisSystemException | QueryTimeoutException e) {
			destineAuditService.saveDestine(new Date(),lat, lng, maxDistance, preference, maxCrowdLevel, busyMin, false);
			System.err.println("Error de conexión a Redis: " + e.getMessage());

			return bestTimedestineService.getFilteredVenuesWithoutCache(lat, lng, maxDistance, preference,
					maxCrowdLevel, busyMin, bestTimeApiKey);
		}
	}
}
