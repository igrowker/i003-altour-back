package com.igrowker.altour.dtos.external.bestTimeApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenClose {
    private Integer opens;
    private Integer closes;
}
