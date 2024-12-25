package com.kdigital.factoryPick.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.kdigital.factoryPick.dto.ComplexTransDTO;

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
@Table(name = "complex_trans")
public class ComplexTransEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // AUTO_INCREMENT
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "complex_name", length = 200, nullable = false)
    private String complexName;

    @Column(name = "highway_score",nullable = false)
    private Double highwayScore;

    @Column(name = "port_score", nullable = false)
    private Double portScore;

    @Column(name = "train_score", nullable = false)
    private Double trainScore;

    @Column(name = "airport_score", nullable = false)
    private Double airportScore;

    @Column(name = "final_score", nullable = false)
    private Double finalScore;

    @Column(name = "complex_group", length = 20, nullable = false)
    private String complexGroup;

    // DTO -> Entity 변환
    public static ComplexTransEntity toEntity(ComplexTransDTO dto) {
        return ComplexTransEntity.builder()
                .complexName(dto.getComplexName())
                .highwayScore(dto.getHighwayScore())
                .portScore(dto.getPortScore())
                .trainScore(dto.getTrainScore())
                .airportScore(dto.getAirportScore())
                .finalScore(dto.getFinalScore())
                .complexGroup(dto.getComplexGroup())
                .build();
    }
}
