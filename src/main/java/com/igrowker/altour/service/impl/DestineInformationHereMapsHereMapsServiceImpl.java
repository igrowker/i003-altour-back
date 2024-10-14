package com.igrowker.altour.service.impl;

import com.igrowker.altour.dtos.external.Items;
import com.igrowker.altour.service.IDestineInformationHereMapsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.igrowker.altour.dtos.external.Item;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

// works with HERE MAPS API
@Service
public class DestineInformationHereMapsHereMapsServiceImpl implements IDestineInformationHereMapsService {

	private final WebClient webClient;

	@Value("${here_maps.api.key}")
	private String hereMapsApiKey;

	public DestineInformationHereMapsHereMapsServiceImpl(WebClient.Builder webClientBuilder) {
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

	@Override
	public Mono<List<Item>> getDestinations(Double lat, Double lng, Integer rad, String activity, String hereMapsApiKey) {
		String uri = String.format("/discover?in=circle:%s,%s;r=%d&q=%s&apiKey=%s", lat, lng, rad, activity, hereMapsApiKey);

		/*
		todo pendiente de obtener Description(), Price(), OpeningHours(), DestinationType().. , revisar manejo de nulos
		 */
		return webClient.get().uri(uri).retrieve().bodyToMono(Items.class)
				.map(items -> items.getItems().stream().map(i -> new Item(
						i.getTitle(),
						i.getDistance(),
						i.getPosition(),
						i.getAddress(),
						i.getContacts()
				)).collect(Collectors.toList()));
	}
}
