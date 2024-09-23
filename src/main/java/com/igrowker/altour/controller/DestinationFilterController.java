package com.igrowker.altour.controller;


import java.util.List;

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

import reactor.core.publisher.Mono;

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
	public Mono<List<Venue>> filterBestTime(@RequestParam Double lat,
											@RequestParam Double lng,
											@RequestParam String preference,
											Authentication authentication) {
		CustomUser userDetails = (CustomUser) authentication.getPrincipal();

		// TODO  ".switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")));" ESTA PARTE NO ESTOY SEGURO QUE HACE, PERO DEBERIA SER IMPOSIBLE QUE TIRE EXCEP DE USER NOT FOUND PORQUE YA TRAE JWT

		// TODO ESTA TIRANDO ERROR Error calling BestTime API: 404 Not Found from GET https://besttime.app/api/v1/venues/filter, SERA POR EL FILTRO? PEDIR AYUDA A PABLO PARA ENTENDER LA API
		return Mono.justOrEmpty(customUserRepository.findByEmail(userDetails.getUsername())).flatMap(user -> {
			Integer maxCrowdLevel = user.getPreferredCrowdLevel();
			Integer maxDistance = user.getMaxSearchDistance();
			return bestTimedestineService.getFilteredVenues(lat, lng, maxDistance, preference, maxCrowdLevel,
					bestTimeApiKey);
		}).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")));
	}


	// TODO reciobir un id y segun el tipo llamar a here o besttime
	// TODO reciobir un id y segun el tipo llamar a here o besttime
	// TODO reciobir un id y segun el tipo llamar a here o besttime
	// TODO reciobir un id y segun el tipo llamar a here o besttime
	@GetMapping("/{placeId}")
	public Mono<Item> getDestinatioe4nInfo(@PathVariable String placeId) {
		// TODO reciobir un id y segun el tipo llamar a here o besttime
		/*
		// works with BEST TIME API: todo QUE INFORMACION DE NEGOCIO RETORNA ESTE ENDPOINT?
		@GetMapping("/venue/{id}")
		public Mono<VenueResponse> getDestinationById(@PathVariable String id) {
			return bestTimedestineService.getVenueById(id, bestTimeApiPubKey);
		}

		// works with HERE MAPS API:  Devuelve información detallada de un punto de interés turístico específico
		@GetMapping("/details/{placeId}")
		public Mono<Item> getDestinationInfo(@PathVariable String placeId) {
			return hereMapsDestineService.getDetailedDestinationInfo(placeId);
		}
	 	*/
		return hereMapsDestineService.getDetailedDestinationInfo(placeId);
	}
}
