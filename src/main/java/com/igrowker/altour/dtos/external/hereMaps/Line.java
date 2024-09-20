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
public class Line {
    @JsonProperty("points")
    private List<GoogleMapsPoint> points;
}

  /*
   {  points : [GoogleMapsPoint,GoogleMapsPoint,GoogleMapsPoint,GoogleMapsPoint] }
"links": [ {
points :[GoogleMapsPoint,GoogleMapsPoint,GoogleMapsPoint,GoogleMapsPoint] },  {},{},{},{},]
        "links": [
        {
        "points": [
        {
        "lat": -31.53342,
        "lng": -68.51507
        },
        {
        "lat": -31.53317,
        "lng": -68.5151
        },
        {
        "lat": -31.53275,
        "lng": -68.51514
        },
        {
        "lat": -31.53221,
        "lng": -68.51519
        }
        ],
        "length": 135.0
        },
        {}
        ]
     */