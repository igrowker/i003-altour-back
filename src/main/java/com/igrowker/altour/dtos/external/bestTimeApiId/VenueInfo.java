package com.igrowker.altour.dtos.external.bestTimeApiId;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueInfo {

	@JsonProperty("venue_id")
	private String venueId;

	@JsonProperty("venue_name")
	private String venueName;

	@JsonProperty("venue_address")
	private String venueAddress;

	@JsonProperty("venue_timezone")
	private String venueTimezone;

	@JsonProperty("venue_lat")
	private double venueLat;

	@JsonProperty("venue_lng")
	private double venueLng;

	@JsonProperty("venue_dwell_time_min")
	private int venueDwellTimeMin;

	@JsonProperty("venue_dwell_time_max")
	private int venueDwellTimeMax;

	@JsonProperty("venue_type")
	private String venueType;

	@JsonProperty("venue_types")
	private List<String> venueTypes;

	@JsonProperty("venue_current_localtime_iso")
	private String venueCurrentLocaltimeIso;

	@JsonProperty("venue_current_gmttime")
	private String venueCurrentGmtTime;
}
