package com.igrowker.altour.api.externalDtos;

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
