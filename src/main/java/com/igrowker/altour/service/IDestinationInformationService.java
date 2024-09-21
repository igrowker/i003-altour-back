package com.igrowker.altour.service;

import com.igrowker.altour.dtos.external.Item;

import reactor.core.publisher.Mono;

public interface IDestinationInformationService {

	Mono<Item> getDetailedDestinationInfo(String placeId);
}
