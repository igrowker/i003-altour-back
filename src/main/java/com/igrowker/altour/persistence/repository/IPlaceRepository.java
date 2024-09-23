package com.igrowker.altour.persistence.repository;

import com.igrowker.altour.persistence.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPlaceRepository extends JpaRepository<Place, Integer> {

    Optional<Place> findPlaceByExternalID(String externalID);
}
