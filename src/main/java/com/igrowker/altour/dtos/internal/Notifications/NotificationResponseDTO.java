package com.igrowker.altour.dtos.internal.Notifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {
    private boolean populationChange;
    private long newPopulation;
    private String details;
}
