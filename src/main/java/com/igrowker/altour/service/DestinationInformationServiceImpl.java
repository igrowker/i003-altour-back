package com.igrowker.altour.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.igrowker.altour.api.externalDtos.BORRAR.Item;

import reactor.core.publisher.Mono;

@Service
public class DestinationInformationServiceImpl implements IDestinationInformationService{

	/*
	 * Gestiona la obtención de información detallada sobre puntos de interés
	 * turístico, incluyendo horarios, precios y descripciones.
	 */

	private final WebClient webClient;

	@Value("${here_maps.api.key}")
	private String hereMapsApiKey;

	public DestinationInformationServiceImpl(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://lookup.search.hereapi.com/v1").build();
	}

	@Override
	public Mono<Item> getDetailedDestinationInfo(String placeId) {
		String uri = String.format("/lookup?id=%s&apiKey=%s", placeId, hereMapsApiKey);

		return webClient.get().uri(uri).retrieve().bodyToMono(Item.class) 
				.map(itemDetails -> new Item(itemDetails.getTitle(), 
						itemDetails.getDistance(), 
						itemDetails.getPosition(), 
						itemDetails.getAddress(),
						itemDetails.getContacts()
				));
	}
}
