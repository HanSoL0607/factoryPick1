package com.kdigital.factoryPick.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportationPreferenceDTO {

    private int highwayPreference;   // 고속도로 인접도
    private int railwayPreference;   // 철도역 인접도
    private int portPreference;      // 항만 인접도
    private int airportPreference;   // 공항 인접도

}
