package com.igrowker.altour;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.service.IDestineBestTimeService;
import com.igrowker.altour.service.impl.DestineRecommendationServiceImpl;

public class DestineRecommendationServiceImplTest {

	@InjectMocks
	private DestineRecommendationServiceImpl recommendationService;

	@Mock
	private IDestineBestTimeService bestTimeService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/*
	 * Verificamos que devuelve el número correcto de recomendacions, la api
	 * directamente por detrás ya trae los resultados siempre con una menor
	 * afluencia de gente.
	 */
	@Test
	public void testGetRecommendations_ReturnsCorrectVenues() {
		Double lat = 40.4168;
		Double lng = -3.7038;
		Integer maxDistance = 500;
		List<String> userPreferences = Arrays.asList("restaurant", "museum");
		Integer maxCrowdLevel = 20;
		Integer busyMin = 1;
		String apiKey = "testApiKey";

		Venue venue1 = new Venue();
		Venue venue2 = new Venue();
		List<Venue> mockVenues = Arrays.asList(venue1, venue2);

		when(bestTimeService.getFilteredVenuesWithoutCache(lat, lng, maxDistance, "restaurant", maxCrowdLevel, busyMin,
				apiKey)).thenReturn(mockVenues);
		when(bestTimeService.getFilteredVenuesWithoutCache(lat, lng, maxDistance, "museum", maxCrowdLevel, busyMin,
				apiKey)).thenReturn(mockVenues);

		List<Venue> recommendations = recommendationService.getRecommendations(lat, lng, maxDistance, userPreferences,
				maxCrowdLevel, busyMin, apiKey);

		assertEquals(4, recommendations.size());
		verify(bestTimeService, times(2)).getFilteredVenuesWithoutCache(anyDouble(), anyDouble(), anyInt(), anyString(),
				anyInt(), anyInt(), anyString());

	}

	// Verificamos que devuelva una lista vacía cuando no hay preferencias..

	@Test
	public void testGetRecommendations_EmptyUserPreferences_ReturnsEmptyList() {
		Double lat = 40.4168;
		Double lng = -3.7038;
		Integer maxDistance = 500;
		List<String> userPreferences = Arrays.asList();
		Integer maxCrowdLevel = 50;
		Integer busyMin = 0;
		String apiKey = "testApiKey";

		List<Venue> recommendations = recommendationService.getRecommendations(lat, lng, maxDistance, userPreferences,
				maxCrowdLevel, busyMin, apiKey);

		assertEquals(0, recommendations.size());
		verify(bestTimeService, never()).getFilteredVenuesWithoutCache(anyDouble(), anyDouble(), anyInt(), anyString(),
				anyInt(), anyInt(), anyString());
	}
}
