package com.igrowker.altour.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.service.IDestineBestTimeService;
import com.igrowker.altour.service.IDestineRecommendationService;

@Service
public class DestineRecommendationServiceImpl implements IDestineRecommendationService {

	@Autowired
	private IDestineBestTimeService bestTimeService;

	/**
	 * Genera recomendaciones basadas en las preferencias del usuario.
	 */
	@Override
	@Cacheable(value = "recommendationsCache", key = "T(java.lang.Math).round(#lat * 100) + '-' + T(java.lang.Math).round(#lng * 100) + '-' + #maxDistance + '-' + #userPreferences.toString() + '-' + #crowdLevel", unless = "#result == null || #result.isEmpty()")
	public List<Venue> getRecommendations(Double lat, Double lng, Integer maxDistance, List<String> userPreferences,
			Integer maxCrowdLevel, Integer busyMin, String apiKey) {
		List<Venue> recommendedVenues = new ArrayList<>();
		int maxVenuesPerPreference = 2;

		for (String preference : userPreferences) {
			List<Venue> venuesForPreference = bestTimeService.getFilteredVenuesWithCache(lat, lng, maxDistance,
					preference, maxCrowdLevel, busyMin, apiKey);

			List<Venue> limitedVenues = venuesForPreference.stream().limit(maxVenuesPerPreference)
					.collect(Collectors.toList());

			recommendedVenues.addAll(limitedVenues);
		}

		return recommendedVenues;
	}

	public List<Venue> getRecommendationsWithoutCache(Double lat, Double lng, Integer maxDistance,
			List<String> userPreferences, Integer maxCrowdLevel, Integer busyMin, String apiKey) {
		List<Venue> recommendedVenues = new ArrayList<>();
		int maxVenuesPerPreference = 2;

		for (String preference : userPreferences) {
			List<Venue> venuesForPreference = bestTimeService.getFilteredVenuesWithoutCache(lat, lng, maxDistance,
					preference, maxCrowdLevel, busyMin, apiKey);

			List<Venue> limitedVenues = venuesForPreference.stream().limit(maxVenuesPerPreference)
					.collect(Collectors.toList());

			recommendedVenues.addAll(limitedVenues);
		}

		return recommendedVenues;
	}
}
