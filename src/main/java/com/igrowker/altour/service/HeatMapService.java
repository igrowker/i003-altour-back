package com.igrowker.altour.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.igrowker.altour.api.externalDtos.JamDataDTO;
import com.igrowker.altour.api.externalDtos.Results;

import reactor.core.publisher.Mono;

@Service
public class HeatMapService {

	private final WebClient webClient;

	public HeatMapService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://data.traffic.hereapi.com/v7/").build();
	}

	public Mono<List<JamDataDTO>> getJamDestinations(String hereMapsApiKey, Double lat, Double lng, Integer rad) {
		String uri = String.format("flow?apiKey=%s&in=circle:%s,%s;r=%d&q=all&locationReferencing=shape", hereMapsApiKey, lat, lng, rad);

		return webClient
				.get().uri(
						uri)
				.retrieve().bodyToMono(Results.class)
				.map(results -> results.getResults().stream()
						.map(result -> new JamDataDTO(result.getLocation().getDescription(),
								result.getCurrentFlow().getJamFactor()))
						.collect(Collectors.toList()));
	}
}
