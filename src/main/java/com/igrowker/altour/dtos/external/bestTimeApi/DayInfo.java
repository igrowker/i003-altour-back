package com.igrowker.altour.dtos.external.bestTimeApi;



import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayInfo {
	@JsonProperty("day_int")
	private Integer dayInt;

	@JsonProperty("day_max")
	private Integer dayMax;

	@JsonProperty("day_mean")
	private Integer dayMean;

	@JsonProperty("day_rank_max")
	private Integer dayRankMax;

	@JsonProperty("day_rank_mean")
	private Integer dayRankMean;

	@JsonProperty("day_text")
	private String dayText;

	@JsonProperty("note")
	private String note;

	@JsonProperty("venue_closed")
	private Integer venueClosed;

	@JsonProperty("venue_open")
	private Integer venueOpen;

	@JsonProperty("venue_open_close_v2")
	private VenueOpenClose venueOpenClose;
}
