package com.igrowker.altour.service.impl;

import com.igrowker.altour.persistence.entity.Place;
import com.igrowker.altour.persistence.repository.IPlaceRepository;
import com.igrowker.altour.service.IPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PlaceServiceImpl implements IPlaceService {
	@Autowired
	IPlaceRepository placeRepository;

	@Override
	@Transactional(readOnly = true)
	public Optional<Place> findPlaceByExternalID(String idPlace) {
		return placeRepository.findByExternalID(idPlace);
	}

	@Override
	@Transactional
	public Place save(Place place) {
		return placeRepository.save(place);
	}

}
