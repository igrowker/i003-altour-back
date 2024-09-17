package com.igrowker.altour.api.externalDtos.frontend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeatMapPoint {
    private GoogleMapsPoint location;
    private int weight;
}
