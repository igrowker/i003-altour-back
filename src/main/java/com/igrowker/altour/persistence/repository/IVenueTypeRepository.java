package com.igrowker.altour.persistence.repository;

import com.igrowker.altour.dtos.external.bestTimeApi.EnumVenueTypes;
import com.igrowker.altour.persistence.entity.VenueType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVenueTypeRepository extends JpaRepository<VenueType, Integer> {
    Optional<VenueType> findByVenueType(String venueType);
}
