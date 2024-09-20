package com.igrowker.altour.dtos.external.hereMaps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HereMapsResults {
    private List<HereMapResult> results;
}
