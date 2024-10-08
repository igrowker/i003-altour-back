package com.igrowker.altour.service;

import java.util.List;

import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueResponse;

import reactor.core.publisher.Mono;

// works with BEST TIME API
public interface IDestineBestTimeService {
	/*
	 * TODO ESTO LO DEJO COMENTADO HASTA VERIFICAR SI LO USAREMOS O NO...
	 * Mono<List<Item>> getDestinations(Double lat, Double lng, Integer rad, String
	 * activity, String hereMapsApiKey);
	 */

	List<Venue> getFilteredVenuesWithCache(Double lat, Double lng, Integer maxDistance, String preference,
			Integer maxCrowdLevel, Integer busyMin, String apiKey);

	List<Venue> getFilteredVenuesWithoutCache(Double lat, Double lng, Integer maxDistance, String preference,
			Integer maxCrowdLevel, Integer busyMin, String apiKey);

	VenueResponse getVenueById(String id, String apiKey);
}
