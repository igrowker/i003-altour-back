package com.igrowker.altour.api.externalDtos.hereMaps.destinyDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {
    private List<String> value;
}
