package com.igrowker.altour.api.externalDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igrowker.altour.api.externalDtos.hereMaps.CurrentFlow;
import com.igrowker.altour.api.externalDtos.hereMaps.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

	@JsonProperty("location")
	private Location location;

	@JsonProperty("currentFlow")
	private CurrentFlow currentFlow;

}
