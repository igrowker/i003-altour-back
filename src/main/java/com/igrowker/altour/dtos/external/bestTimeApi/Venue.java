package com.igrowker.altour.dtos.external.bestTimeApi;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Venue {

	@JsonProperty("venue_id")
	private String venueId;

	@JsonProperty("venue_name")
	private String venueName;

	@JsonProperty("venue_address")
	private String venueAddress;

	@JsonProperty("venue_lat")
	private Double venueLat;

	@JsonProperty("venue_lng")
	private Double venueLng;

	@JsonProperty("price_level")
	private Integer priceLevel;

	private Double rating;

	private Integer reviews;

	@JsonProperty("venue_type")
	private String venueType;

	@JsonProperty("day_raw")
	@Builder.Default
	private List<Integer> dayRaw = new ArrayList<>();

	@JsonProperty("day_raw_whole")
	@Builder.Default
	private List<Integer> dayRawWhole = new ArrayList<>();

	@JsonProperty("day_info")
	private DayInfo dayInfo;
}
