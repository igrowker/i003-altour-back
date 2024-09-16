package com.igrowker.altour.api.externalDtos.hereMaps;

import com.igrowker.altour.api.externalDtos.Result;
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
