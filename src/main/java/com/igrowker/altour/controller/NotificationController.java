package com.igrowker.altour.controller;

import com.igrowker.altour.dtos.internal.Notifications.NotificationRequestDTO;
import com.igrowker.altour.dtos.internal.Notifications.NotificationResponseDTO;
import com.igrowker.altour.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private INotificationService NotificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> checkPopulationChange(NotificationRequestDTO request) {

        return new ResponseEntity<>(NotificationService.checkPopulationChange(request), HttpStatus.OK);
    }
}
