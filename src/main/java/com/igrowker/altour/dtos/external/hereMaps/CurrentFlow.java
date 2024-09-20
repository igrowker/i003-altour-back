package com.igrowker.altour.dtos.external.hereMaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentFlow {
	@JsonProperty("jamFactor")
	private Double jamFactor;
}
