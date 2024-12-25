package com.kdigital.factoryPick.dto;
import com.kdigital.factoryPick.entity.TransInfoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransInfoDTO {

    private Long id;
    private String complexName;
    private String complexGroup;
    private String airportName;
    private String airportDistance;
    private String seaportName;
    private String seaportDistance;
    private String highwayName;
    private String highwayDistance;
    private String stationName;
    private String stationDistance;

    // Entity -> DTO 변환 메서드
    public static TransInfoDTO toDTO(TransInfoEntity entity) {
        return TransInfoDTO.builder()
                .id(entity.getId())
                .complexName(entity.getComplexName())
                .complexGroup(entity.getComplexGroup())
                .airportName(entity.getAirportName())
                .airportDistance(entity.getAirportDistance())
                .seaportName(entity.getSeaportName())
                .seaportDistance(entity.getSeaportDistance())
                .highwayName(entity.getHighwayName())
                .highwayDistance(entity.getHighwayDistance())
                .stationName(entity.getStationName())
                .stationDistance(entity.getStationDistance())
                .build();
    }

}
