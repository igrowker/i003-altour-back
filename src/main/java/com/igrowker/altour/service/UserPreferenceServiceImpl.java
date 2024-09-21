package com.igrowker.altour.service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.UserPreference;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;
import com.igrowker.altour.persistence.repository.IUserPreferenceRepository;

@Service
public class UserPreferenceServiceImpl implements IUserPreferenceService {

	@Autowired
	private IUserPreferenceRepository userPreferenceRepository;

	@Autowired
	private ICustomUserRepository customUserRepository;

	@Override
	public void addPreference(String username, String newPreference) {
		Optional<CustomUser> user = customUserRepository.findByUsername(username);
		if (user.isPresent()) {
			CustomUser userPresent = user.get();
			UserPreference preference = new UserPreference();
			preference.setUser(userPresent);
			preference.setPreference(newPreference);
			userPreferenceRepository.save(preference);
		}
	}

	@Override
	public void removePreference(String username, String preferenceToRemove) {
		Optional<CustomUser> user = customUserRepository.findByUsername(username);
		if (user.isPresent()) {
			CustomUser userPresent = user.get();
			userPreferenceRepository.deleteByUserAndPreference(userPresent, preferenceToRemove);
		}
	}

	@Override
	public Set<String> getPreferences(String username) {
		Optional<CustomUser> user = customUserRepository.findByUsername(username);
		if (user.isPresent()) {
			CustomUser userPresent = user.get();
			Set<UserPreference> preferences = userPreferenceRepository.findByUser(userPresent);
			return preferences.stream().map(UserPreference::getPreference).collect(Collectors.toSet());
		}
		return Collections.emptySet();
	}

}
