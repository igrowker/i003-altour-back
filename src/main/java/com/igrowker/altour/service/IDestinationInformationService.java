package com.igrowker.altour.service;

import com.igrowker.altour.api.externalDtos.BORRAR.Item;

import reactor.core.publisher.Mono;

public interface IDestinationInformationService {

	Mono<Item> getDetailedDestinationInfo(String placeId);
}
