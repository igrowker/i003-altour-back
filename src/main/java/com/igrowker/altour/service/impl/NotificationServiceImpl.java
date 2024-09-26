package com.igrowker.altour.service.impl;

import com.igrowker.altour.dtos.internal.Notifications.NotificationRequestDTO;
import com.igrowker.altour.dtos.internal.Notifications.NotificationResponseDTO;
import com.igrowker.altour.service.INotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Override
    public NotificationResponseDTO checkPopulationChange(NotificationRequestDTO request) {
        return NotificationResponseDTO.builder()
                .populationChange(true)
                .details("Population has changed")
                .build();
    }
}
