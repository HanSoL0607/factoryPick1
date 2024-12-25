package com.kdigital.factoryPick.dto;
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
public class ComplexTransDTO {

    private Integer id;
    private String complexName;
    private Double highwayScore;
    private Double portScore;
    private Double trainScore;
    private Double airportScore;
    private Double finalScore;
    private String complexGroup;
}
