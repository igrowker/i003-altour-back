package com.igrowker.altour.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.igrowker.altour.dtos.external.Item;
import com.igrowker.altour.dtos.external.Items;

import reactor.core.publisher.Mono;

@Service
public class DestinationFilterService {

	private final WebClient webClient;

	public DestinationFilterService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://discover.search.hereapi.com/v1/").build();
	}

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