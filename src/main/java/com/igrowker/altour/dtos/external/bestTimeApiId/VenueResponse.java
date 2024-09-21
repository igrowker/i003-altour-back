package com.igrowker.altour.dtos.external.bestTimeApiId;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueResponse {

	@JsonProperty("venue_info")
	private VenueInfo venueInfo;

	@JsonProperty("forecast_updated_on")
	private String forecastUpdatedOn;

	@JsonProperty("venue_forecasted")
	private boolean venueForecasted;

	@JsonProperty("epoch_analysis")
	private long epochAnalysis;

	@JsonProperty("status")
	private String status;
}