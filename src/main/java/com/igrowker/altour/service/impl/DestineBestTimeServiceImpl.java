package com.igrowker.altour.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.igrowker.altour.exceptions.NotFoundException;
import com.igrowker.altour.service.IDestineBestTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.dtos.external.bestTimeApi.VenuesResponse;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueInfo;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueResponse;

//works with BEST TIME API
@Service
public class DestineBestTimeServiceImpl implements IDestineBestTimeService {

	@Value("${besttime.api.url}")
	private String bestTimeApiUrl;

	@Autowired
	private RestTemplate restTemplate;

	private List<Venue> getVenues(Double lat, Double lng, Integer maxDistance, String preference, Integer maxCrowdLevel,
			Integer busyMin, String apiKey) {
		String latStr = String.valueOf(lat).replace(",", ".");
		String lngStr = String.valueOf(lng).replace(",", ".");

		StringBuilder uriBuilder = new StringBuilder(String.format(
				"https://besttime.app/api/v1/venues/filter?api_key_private=%s&busy_min=%d&busy_max=%d&lat=%s&lng=%s&radius=%d&now=true&busy_conf=all&limit=100",
				apiKey, busyMin, maxCrowdLevel, latStr, lngStr, maxDistance));

		if (preference != null && !preference.isEmpty()) {
			uriBuilder.append("&types=").append(preference);
		}

		String uri = uriBuilder.toString();

		ResponseEntity<VenuesResponse> response = restTemplate.exchange(uri, HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders()), VenuesResponse.class);

		if (response.getBody() != null && response.getBody().getVenues() != null) {
			return response.getBody().getVenues().stream()
					.map(venue -> new Venue(venue.getVenueId(), venue.getVenueName(), venue.getVenueAddress(),
							venue.getVenueLat(), venue.getVenueLng(), venue.getPriceLevel(), venue.getRating(),
							venue.getReviews(), venue.getVenueType(), venue.getDayRaw(), venue.getDayRawWhole(),
							venue.getDayInfo()))
					.sorted(Comparator.comparingInt(venue -> (venue.getDayRaw() != null && !venue.getDayRaw().isEmpty())
							? venue.getDayRaw().get(0)
							: 0))
					.collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	// Cache para mejorar el tiempo de respuesta -> he puesto un math round para lat
	// y lng porque si varia sola por pocas décimas el cache no funcionaria bien ya
	// que no haria match..
	@Override
	@Cacheable(value = "venuesCache", key = "T(java.lang.Math).round(#lat * 100) + '-' + T(java.lang.Math).round(#lng * 100) + '-' + #maxDistance + '-' + #preference + '-' + #maxCrowdLevel + '-' + #busyMin")
	public List<Venue> getFilteredVenuesWithCache(Double lat, Double lng, Integer maxDistance, String preference,
			Integer maxCrowdLevel, Integer busyMin, String apiKey) {
		return getVenues(lat, lng, maxDistance, preference, maxCrowdLevel, busyMin, apiKey);
	}

	@Override
	public List<Venue> getFilteredVenuesWithoutCache(Double lat, Double lng, Integer maxDistance, String preference,
			Integer maxCrowdLevel, Integer busyMin, String apiKey) {
		return getVenues(lat, lng, maxDistance, preference, maxCrowdLevel, busyMin, apiKey);
	}

	public VenueResponse getVenueById(String id, String apiKey) {
		String uri = String.format("%s?api_key_public=%s", id, apiKey);
		String fullUrl = bestTimeApiUrl + uri;
		System.out.println("Full URL: " + fullUrl);

		try {
			ResponseEntity<VenueResponse> response = restTemplate.exchange(fullUrl, HttpMethod.GET,
					new HttpEntity<>(new HttpHeaders()), VenueResponse.class);

			if (response.getBody() != null) {
				VenueResponse venueResponse = response.getBody();
				VenueInfo venueInfo = venueResponse.getVenueInfo();

				return VenueResponse.builder()
						.venueInfo(VenueInfo.builder().venueId(venueInfo.getVenueId())
								.venueName(venueInfo.getVenueName()).venueAddress(venueInfo.getVenueAddress())
								.venueTimezone(venueInfo.getVenueTimezone()).venueLat(venueInfo.getVenueLat())
								.venueLng(venueInfo.getVenueLng()).venueDwellTimeMin(venueInfo.getVenueDwellTimeMin())
								.venueDwellTimeMax(venueInfo.getVenueDwellTimeMax()).venueType(venueInfo.getVenueType())
								.venueTypes(venueInfo.getVenueTypes())
								.venueCurrentLocaltimeIso(venueInfo.getVenueCurrentLocaltimeIso())
								.venueCurrentGmtTime(venueInfo.getVenueCurrentGmtTime()).build())
						.forecastUpdatedOn(venueResponse.getForecastUpdatedOn())
						.venueForecasted(venueResponse.isVenueForecasted())
						.epochAnalysis(venueResponse.getEpochAnalysis()).status(venueResponse.getStatus()).build();
			} else {
				throw new NotFoundException("No se encontraron datos para el venue con ID: " + id);
			}
		} catch (RestClientException e) {
			System.err.println("Error fetching venue by ID: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching venue by ID", e);
		}
	}

}
