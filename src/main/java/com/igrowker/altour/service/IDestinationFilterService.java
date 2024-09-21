package com.igrowker.altour.service;

import java.util.List;
import com.igrowker.altour.dtos.external.Item;
import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueResponse;

import reactor.core.publisher.Mono;

public interface IDestinationFilterService {

	Mono<List<Item>> getDestinations(Double lat, Double lng, Integer rad, String activity, String hereMapsApiKey);

	Mono<List<Venue>> getFilteredVenues(Double lat, Double lng, Integer maxDistance, String preference,
			Integer maxCrowdLevel, String apiKey);

	Mono<VenueResponse> getVenueById(String id, String apiKey);
}
