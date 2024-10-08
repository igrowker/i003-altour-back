package com.igrowker.altour.dtos.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.igrowker.altour.dtos.external.hereMaps.CurrentFlow;
import com.igrowker.altour.dtos.external.hereMaps.Location;
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
