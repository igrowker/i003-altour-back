package com.igrowker.altour.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.igrowker.altour.dtos.external.bestTimeApi.Venue;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.VenueType;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;
import com.igrowker.altour.service.IDestineRecommendationService;
import com.igrowker.altour.utils.AESUtils;

import io.lettuce.core.RedisConnectionException;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("recommendations")
@Tag(name = "RECOMENDACIONES")
public class DestinRecommendationController {

	@Autowired
	private IDestineRecommendationService destineRecommendationService;

	@Autowired
	private ICustomUserRepository customUserRepository;

	@Value("${best_time.api.key}")
	private String bestTimeApiKey;

	@Autowired
	private AESUtils aesUtils;

	@GetMapping("/")
	public List<Venue> getRecommendations(@RequestParam Double lat, @RequestParam Double lng,
			Authentication authentication) {

		if (authentication == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
		}

		CustomUser userDetails = (CustomUser) authentication.getPrincipal();
		Optional<CustomUser> userOptional = customUserRepository.findByEmail(userDetails.getUsername());

		if (userOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}

		CustomUser user = userOptional.get();

		Integer crowdLevel = user.getPreferredCrowdLevel();
		Integer maxDistance = user.getMaxSearchDistance();

		Set<VenueType> encryptedUserPreferences = user.getPreferences();

		List<String> decryptedPreferences = new ArrayList<>();

		for (VenueType encryptedPreference : encryptedUserPreferences) {
			String decryptedPreference = aesUtils.decrypt(encryptedPreference.getVenueType());
			decryptedPreferences.add(decryptedPreference);
		}

		try {
			return destineRecommendationService.getRecommendations(lat, lng, maxDistance, decryptedPreferences,
					crowdLevel, 1, bestTimeApiKey);
		} catch (RedisConnectionFailureException | RedisConnectionException | RedisSystemException
				| QueryTimeoutException e) {
			System.err.println("Error de conexión a Redis, usando servicio sin caché: " + e.getMessage());
			return destineRecommendationService.getRecommendationsWithoutCache(lat, lng, maxDistance,
					decryptedPreferences, crowdLevel, 1, bestTimeApiKey);
		}
	}
}
