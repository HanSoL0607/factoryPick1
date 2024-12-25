package com.kdigital.factoryPick.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.kdigital.factoryPick.dto.TransInfoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "trans_info")
public class TransInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "complex_name", length = 200, nullable = false)
    private String complexName;

    @Column(name = "complex_group", length = 200)
    private String complexGroup;

    @Column(name = "airport_name", length = 200)
    private String airportName;

    @Column(name = "airport_distance", length = 20)
    private String airportDistance;

    @Column(name = "seaport_name", length = 200)
    private String seaportName;

    @Column(name = "seaport_distance", length = 20)
    private String seaportDistance;

    @Column(name = "highway_name", length = 200)
    private String highwayName;

    @Column(name = "highway_distance", length = 20)
    private String highwayDistance;

    @Column(name = "station_name", length = 200)
    private String stationName;

    @Column(name = "station_distance", length = 20)
    private String stationDistance;
    

    // DTO -> Entity 변환 메서드
    public static TransInfoEntity toEntity(TransInfoDTO dto) {
        return TransInfoEntity.builder()
                .complexName(dto.getComplexName())
                .complexGroup(dto.getComplexGroup())
                .airportName(dto.getAirportName())
                .airportDistance(dto.getAirportDistance())
                .seaportName(dto.getSeaportName())
                .seaportDistance(dto.getSeaportDistance())
                .highwayName(dto.getHighwayName())
                .highwayDistance(dto.getHighwayDistance())
                .stationName(dto.getStationName())
                .stationDistance(dto.getStationDistance())
                .build();
    }
    
}
