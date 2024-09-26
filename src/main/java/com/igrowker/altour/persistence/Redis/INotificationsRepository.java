package com.igrowker.altour.persistence.Redis;

import java.util.Map;

public interface INotificationsRepository {
    UserLocationAndPopulation getUserLocationAndPopulation(String userId);
    void setUserLocationAndPopulation(String userId, Map<String, String> userLocationAndPopulation);
}
