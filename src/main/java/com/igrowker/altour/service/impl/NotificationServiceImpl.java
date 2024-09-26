package com.igrowker.altour.service.impl;

import com.igrowker.altour.dtos.internal.Notifications.NotificationRequestDTO;
import com.igrowker.altour.dtos.internal.Notifications.NotificationResponseDTO;
import com.igrowker.altour.persistence.Redis.INotificationsRepository;
import com.igrowker.altour.persistence.Redis.UserLocationAndPopulation;
import com.igrowker.altour.service.INotificationService;
import com.igrowker.altour.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.HikariCheckpointRestoreLifecycle;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    INotificationsRepository notificationsRepository;

    @Autowired
    IUserService userService;
    @Autowired
    private HikariCheckpointRestoreLifecycle hikariCheckpointRestoreLifecycle;

    @Override
    public NotificationResponseDTO checkPopulationChange(NotificationRequestDTO request) {

        NotificationResponseDTO response = new NotificationResponseDTO();

        String userId = userService.getUserByEmail(request.getEmail()).getId().toString();

        if (userId.isEmpty()) {
            return null;
        }

        UserLocationAndPopulation oldData = notificationsRepository.getUserLocationAndPopulation(userId);

        if (oldData == null) {
            return null;
        }

        if (oldData.getLat() != request.getLat() || oldData.getLng() != request.getLng()) {
            Map<String, String> userLocationUpdate = new HashMap<String, String>();
            userLocationUpdate.put("lat", String.valueOf(request.getLat()));
            userLocationUpdate.put("lng", String.valueOf(request.getLng()));
            userLocationUpdate.put("population", String.valueOf(request.getPopulation()));
            notificationsRepository.setUserLocationAndPopulation(userId, userLocationUpdate);
        }

        UserLocationAndPopulation newData = new UserLocationAndPopulation();
        newData.setLat(request.getLat());
        newData.setLng(request.getLng());

        // TODO: A DONDE LLAMAR PARA TENER LA AFLUENCIA DE GENTE EN ESA POSICION EN PARTICULAR
        newData.setPopulation(request.getPopulation());

        if (oldData.getPopulation() >= newData.getPopulation() * 1.1) {
            response.setPopulationChange(true);
            response.setNewPopulation(newData.getPopulation());
            int populationIncrease = 0; //! CALCULAR VARIACION DE PORCENTAJE
            response.setDetails("The population has increased at the location by  " + populationIncrease + "%");
        }
        else if (oldData.getPopulation() <= newData.getPopulation() * 0.9) {
            response.setPopulationChange(true);
            response.setNewPopulation(newData.getPopulation());
            int populationDecrease = 0; //! CALCULAR VARIACION DE PORCENTAJE
            response.setDetails("The population has decreased at the location by  " + populationDecrease + "%");
        }
        else {
            response.setPopulationChange(false);
        }

        return response;
    }
}
