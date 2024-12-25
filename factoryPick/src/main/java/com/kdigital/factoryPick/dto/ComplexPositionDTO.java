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
public class ComplexPositionDTO {

    private Integer id;
    private String complexName;
    private String latitude;
    private String longitude;
}
