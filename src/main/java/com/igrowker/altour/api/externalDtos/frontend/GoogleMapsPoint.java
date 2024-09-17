package com.igrowker.altour.api.externalDtos.frontend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoogleMapsPoint {
    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("lng")
    private Double longuitude;
}
