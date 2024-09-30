package com.igrowker.altour.service;

import com.igrowker.altour.persistence.entity.Place;

import java.util.Optional;

public interface IPlaceService {

	Optional<Place> findPlaceByExternalID(String idPlace);

	Place save(Place place);

}
