package com.igrowker.altour.dtos.external.hereMaps.destinyDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    private List<PhoneValues> phone;
    private List<WwwValues> www;
}
