package com.igrowker.altour.api.externalDtos.hereMaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentFlow {
	@JsonProperty("jamFactor")
	private Double jamFactor;
}
