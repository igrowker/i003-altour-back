package com.igrowker.altour.api.externalDtos.hereMaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HereMapResult {

    @JsonProperty("location")
    private HereMapLocation location;

    @JsonProperty("currentFlow")
    private CurrentFlow currentFlow;
}
