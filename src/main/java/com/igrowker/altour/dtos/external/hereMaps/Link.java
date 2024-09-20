package com.igrowker.altour.dtos.external.hereMaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igrowker.altour.dtos.external.frontend.GoogleMapsPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {
    @JsonProperty("points")
    private List<GoogleMapsPoint> points;

    @JsonProperty("length")
    private double length;
}