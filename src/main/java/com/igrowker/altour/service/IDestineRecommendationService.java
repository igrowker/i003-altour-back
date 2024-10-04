package com.igrowker.altour.service;

import java.util.List;

import com.igrowker.altour.dtos.external.bestTimeApi.Venue;

public interface IDestineRecommendationService {

	List<Venue> getRecommendations(Double lat, Double lng, Integer maxDistance, List<String> userPreferences,
			Integer maxCrowdLevel, Integer busyMin, String apiKey);
}
