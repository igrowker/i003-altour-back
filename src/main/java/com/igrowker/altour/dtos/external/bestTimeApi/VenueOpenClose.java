package com.igrowker.altour.dtos.external.bestTimeApi;

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
public class VenueOpenClose {
	@JsonProperty("12h")
	private List<String> hours12h;

	@JsonProperty("24h")
	private List<OpenClose> hours24h;
}