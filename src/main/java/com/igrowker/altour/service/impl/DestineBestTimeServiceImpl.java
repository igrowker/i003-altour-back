package com.igrowker.altour.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.igrowker.altour.service.IDestineBestTimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.dtos.external.bestTimeApi.VenuesResponse;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueInfo;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueResponse;

import reactor.core.publisher.Mono;

//works with BEST TIME API
@Service
public class DestineBestTimeServiceImpl implements IDestineBestTimeService {

	@Value("${besttime.api.url}")
	private String bestTimeApiUrl;

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * TODO ESTO LO DEJO COMENTADO HASTA VERIFICAR SI LO USAREMOS O NO...
	 * 
	 * @Override public Mono<List<Item>> getDestinations(Double lat, Double lng,
	 * Integer rad, String activity, String hereMapsApiKey) { String uri =
	 * String.format("/discover?in=circle:%s,%s;r=%d&q=%s&apiKey=%s", lat, lng, rad,
	 * activity, hereMapsApiKey);
	 * 
	 * // todo pendiente de obtener Description(), Price(), OpeningHours(),
	 * DestinationType().. , revisar manejo de nulos return
	 * hereMapsWebClient.get().uri(uri).retrieve().bodyToMono(Items.class)
	 * .map(items -> items.getItems().stream().map( i -> new Item(i.getTitle(),
	 * i.getDistance(), i.getPosition(), i.getAddress(), i.getContacts()))
	 * .collect(Collectors.toList())); }
	 */

	@Override
	public List<Venue> getFilteredVenues(Double lat, Double lng, Integer maxDistance, String preference,
			Integer maxCrowdLevel, String apiKey) {

		// Ojo que por defecto interpreta los . como , por eso esta formateado
		String latStr = String.valueOf(lat).replace(",", ".");
		String lngStr = String.valueOf(lng).replace(",", ".");

		// busy_min siempre será 1 evita el 0 para que no salgan valores de sitios que esten cerrados
		int minCrowdLevel = 1;

		  StringBuilder uriBuilder = new StringBuilder(String.format(
				  // busy min pongo 1 para evitar cuando esta cerrado el now = true es para que filtre la ocupacion de la hora correspondiente a ahora limit 100 maximo 500 sitios
				  //pongo en 100 porque en 500 tira un error a veces quizas con la carga del tamaño o espera de la respuesta de resttemplate
		            "https://besttime.app/api/v1/venues/filter?api_key_private=%s&busy_min=%d&busy_max=%d&lat=%s&lng=%s&radius=%d&now=true&busy_conf=all&limit=100",
		            apiKey, minCrowdLevel, maxCrowdLevel, latStr, lngStr, maxDistance));

		    // Solo agrega el parámetro 'types' si se proporciona una preferencia
		    if (preference != null && !preference.isEmpty()) {
		        uriBuilder.append("&types=").append(preference);
		    }

		    String uri = uriBuilder.toString();
		    System.out.println("Full URL: " + uri);
	
		    ResponseEntity<VenuesResponse> response = restTemplate.exchange(
		            uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), VenuesResponse.class);

		    if (response.getBody() != null && response.getBody().getVenues() != null) {
		        return response.getBody().getVenues().stream()
		                .map(venue -> new Venue(
		                        venue.getVenueId(), venue.getVenueName(), venue.getVenueAddress(),
		                        venue.getVenueLat(), venue.getVenueLng(), venue.getPriceLevel(), venue.getRating(),
		                        venue.getReviews(), venue.getVenueType(), venue.getDayRaw(), venue.getDayRawWhole(),
		                        venue.getDayInfo()))
		                .collect(Collectors.toList());
		    } else {
		        return new ArrayList<>();
		    }
	}

	public VenueResponse getVenueById(String id, String apiKey) {
	    String uri = String.format("%s?api_key_public=%s", id, apiKey);
	    String fullUrl = bestTimeApiUrl + uri;
	    System.out.println("Full URL: " + fullUrl);

	    try {
	        ResponseEntity<VenueResponse> response = restTemplate.exchange(
	                fullUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), VenueResponse.class);

	        if (response.getBody() != null) {
	            VenueResponse venueResponse = response.getBody();
	            VenueInfo venueInfo = venueResponse.getVenueInfo(); 
  
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
	        } else {	         
	            throw new RuntimeException("No se encontraron datos para el venue con ID: " + id);
	        }
	    } catch (RestClientException e) {
	        System.err.println("Error fetching venue by ID: " + e.getMessage());
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching venue by ID", e);
	    }
	}
	
}
