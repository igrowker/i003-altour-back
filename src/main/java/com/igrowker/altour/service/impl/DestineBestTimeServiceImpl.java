package com.igrowker.altour.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.igrowker.altour.service.IDestineBestTimeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.dtos.external.bestTimeApi.VenuesResponse;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueInfo;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueResponse;

import reactor.core.publisher.Mono;

// works with BEST TIME API
@Service
public class DestineBestTimeServiceImpl implements IDestineBestTimeService {

	@Value("${besttime.api.url}") 
	private String bestTimeApiUrl;

	// private final WebClient bestTimeWebClient;
	private final WebClient hereMapsWebClient;
	private final WebClient bestTimeWebClient;

	public DestineBestTimeServiceImpl(WebClient.Builder webClientBuilder) {
		this.hereMapsWebClient = webClientBuilder.baseUrl("https://discover.search.hereapi.com/v1/").build();
		this.bestTimeWebClient = webClientBuilder.baseUrl("https://besttime.app/api/v1/venues/").build();
	}
/*
TODO ESTO LO DEJO COMENTADO HASTA VERIFICAR SI LO USAREMOS O NO...
	@Override
	public Mono<List<Item>> getDestinations(Double lat, Double lng, Integer rad, String activity,
			String hereMapsApiKey) {
		String uri = String.format("/discover?in=circle:%s,%s;r=%d&q=%s&apiKey=%s", lat, lng, rad, activity,
				hereMapsApiKey);

		 // todo pendiente de obtener Description(), Price(), OpeningHours(),  DestinationType().. , revisar manejo de nulos
		return hereMapsWebClient.get().uri(uri).retrieve().bodyToMono(Items.class)
				.map(items -> items.getItems().stream().map(
						i -> new Item(i.getTitle(), i.getDistance(), i.getPosition(), i.getAddress(), i.getContacts()))
						.collect(Collectors.toList()));
	}
*/

	@Override
	public Mono<List<Venue>> getFilteredVenues(Double lat, Double lng, Integer maxDistance, String preference,
			Integer maxCrowdLevel, String apiKey) {

		// Ojo que por defecto interpreta los . como , por eso esta formateado
		String latStr = String.valueOf(lat).replace(",", ".");
		String lngStr = String.valueOf(lng).replace(",", ".");

		String uri = String.format("filter?api_key_private=%s&busy_max=%d&types=%s&lat=%s&lng=%s&radius=%d", apiKey,
				maxCrowdLevel, preference, latStr, lngStr, maxDistance);

		String fullUrl = bestTimeApiUrl + uri; 
		System.out.println("Full URL: " + fullUrl);

		// TODO VERIFICAR PASO A PASO DE ACA HACIA ABAJO-- > SIN SEGURIDAD LLEGA BIEN LA RESPONSE, POR QUE?

		return bestTimeWebClient.get().uri(uri).retrieve().bodyToMono(VenuesResponse.class)
				.map(venuesResponse -> venuesResponse.getVenues().stream()
						.map(venue -> new Venue(venue.getVenueId(), venue.getVenueName(), venue.getVenueAddress(),
								venue.getVenueLat(), venue.getVenueLng(), venue.getPriceLevel(), venue.getRating(),
								venue.getReviews(), venue.getVenueType(), venue.getDayRaw(), venue.getDayRawWhole(),
								venue.getDayInfo()))
						.collect(Collectors.toList()))
				.onErrorResume(e -> {
					System.err.println("Error calling BestTime API: " + e.getMessage());
					return Mono.error(new RuntimeException("Failed to get filtered venues", e));
				});
	}

	@Override
	public Mono<VenueResponse> getVenueById(String id, String apiKey) {
		String uri = String.format("%s?api_key_public=%s", id, apiKey);

		// TODO VERIFICAR MANEJO DE EXCEPCIONES POR AQUI.. HAY VARIOS TIPOS DE LA API EXTERNA, DE MAPEO DE RESPUESTA, DE ID ERRONEO

		 return bestTimeWebClient.get().uri(uri).retrieve().bodyToMono(VenueResponse.class)
			        .map(venueResponse -> {
			            VenueInfo venueInfo = venueResponse.getVenueInfo(); // Extrae los datos de VenueInfo

			            return VenueResponse.builder()
			                    .venueInfo(VenueInfo.builder()
			                        .venueId(venueInfo.getVenueId())
			                        .venueName(venueInfo.getVenueName())
			                        .venueAddress(venueInfo.getVenueAddress())
			                        .venueTimezone(venueInfo.getVenueTimezone())
			                        .venueLat(venueInfo.getVenueLat())
			                        .venueLng(venueInfo.getVenueLng())
			                        .venueDwellTimeMin(venueInfo.getVenueDwellTimeMin())
			                        .venueDwellTimeMax(venueInfo.getVenueDwellTimeMax())
			                        .venueType(venueInfo.getVenueType())
			                        .venueTypes(venueInfo.getVenueTypes())
			                        .venueCurrentLocaltimeIso(venueInfo.getVenueCurrentLocaltimeIso())
			                        .venueCurrentGmtTime(venueInfo.getVenueCurrentGmtTime())
			                        .build())
			                    .forecastUpdatedOn(venueResponse.getForecastUpdatedOn())
			                    .venueForecasted(venueResponse.isVenueForecasted())
			                    .epochAnalysis(venueResponse.getEpochAnalysis())
			                    .status(venueResponse.getStatus())
			                    .build();
			        })
			        .onErrorResume(e -> {
			            System.err.println("Error fetching venue by ID: " + e.getMessage());
			            return Mono.empty();
			        });
	}
}
