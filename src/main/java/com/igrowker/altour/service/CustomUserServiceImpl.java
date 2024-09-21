package com.igrowker.altour.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;

@Service
public class CustomUserServiceImpl implements ICustomUserService {

	@Autowired
	private ICustomUserRepository customUserRepository;

	@Override
	public void saveUser(CustomUser customUser) {
		customUserRepository.save(customUser);
	}

	@Override
	public void setMaxDistance(String username, Integer maxDistance) {
		CustomUser user = customUserRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
		user.setMaxSearchDistance(maxDistance);
		customUserRepository.save(user);
	}

	@Override
	public void setCrowdLevel(String username, Integer crowdLevel) {
		CustomUser user = customUserRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
		user.setPreferredCrowdLevel(crowdLevel);
		customUserRepository.save(user);
	}
}
