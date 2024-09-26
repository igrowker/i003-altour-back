package com.igrowker.altour.service;

import com.igrowker.altour.dtos.internal.Notifications.NotificationRequestDTO;
import com.igrowker.altour.dtos.internal.Notifications.NotificationResponseDTO;

public interface INotificationService {
    NotificationResponseDTO checkPopulationChange(NotificationRequestDTO request);
}
