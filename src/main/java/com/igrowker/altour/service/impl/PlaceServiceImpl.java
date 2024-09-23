package com.igrowker.altour.service.impl;

import com.igrowker.altour.persistence.entity.Place;
import com.igrowker.altour.persistence.repository.IPlaceRepository;
import com.igrowker.altour.service.IPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceServiceImpl implements IPlaceService {
    @Autowired
    IPlaceRepository placeRepository;

    @Override
    public Optional<Place> findPlaceByExternalAPI(String idPlace) {
        return  placeRepository.findPlaceByExternalID(idPlace);
    }
}
