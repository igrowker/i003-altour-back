package com.igrowker.altour.dtos.external.frontend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JamDataDTO {
	private String description;
	private double jamFactor;
}
