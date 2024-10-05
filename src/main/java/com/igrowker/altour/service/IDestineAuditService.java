package com.igrowker.altour.service;

import com.igrowker.altour.persistence.entity.DestineAudit;

import java.util.Date;

public interface IDestineAuditService {

    DestineAudit saveDestine (Date dateTime, Double lat, Double lng, Integer maxDistance, String preference, Integer maxCrowdLevel, Integer busyMin, Boolean cachedResult);
}
