package com.igrowker.altour.service;

import com.igrowker.altour.dtos.external.Item;

import reactor.core.publisher.Mono;

import java.util.List;

// works with HERE MAPS API
public interface IDestineInformationHereMapsService {

	Mono<Item> getDetailedDestinationInfo(String placeId);
	Mono<List<Item>> getDestinations(Double lat, Double lng, Integer rad, String activity, String hereMapsApiKey);
}
