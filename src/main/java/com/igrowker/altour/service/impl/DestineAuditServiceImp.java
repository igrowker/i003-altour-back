package com.igrowker.altour.service.impl;

import com.igrowker.altour.persistence.entity.DestineAudit;
import com.igrowker.altour.persistence.repository.IDestineAuditRepository;
import com.igrowker.altour.service.IDestineAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DestineAuditServiceImp implements IDestineAuditService {

    @Autowired
    IDestineAuditRepository repository;

    @Override
    public DestineAudit saveDestine(Date dateTime, Double lat, Double lng, Integer maxDistance, String preference, Integer maxCrowdLevel, Integer busyMin, Boolean cachedResult) {
        DestineAudit destineAudit = DestineAudit.builder()
                .dateTime(dateTime)
                .lat(lat)
                .lng(lng)
                .maxDistance(maxDistance)
                .preference(preference)
                .maxCrowdLevel(maxCrowdLevel)
                .busyMin(busyMin)
                .cachedResult(cachedResult)
                .build();
        return repository.save(destineAudit);
    }
}
