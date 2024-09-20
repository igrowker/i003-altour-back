package com.igrowker.altour.dtos.external.hereMaps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Links {
    // no tiene un nombre epecifico, son array de objetos.. @JsonProperty("points")
    private List<Line> lines;

    // @JsonProperty("points")
    // private List<GoogleMapsPoint> points;

}
