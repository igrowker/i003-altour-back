package com.igrowker.altour.service;


import com.igrowker.altour.persistence.entity.CustomUser;

public interface ICustomUserService {
	void saveUser(CustomUser customUser);

	void setMaxDistance(String username, Integer maxDistance);

	void setCrowdLevel(String username, Integer crowdLevel);
}
