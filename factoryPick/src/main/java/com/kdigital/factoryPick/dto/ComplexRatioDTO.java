package com.kdigital.factoryPick.dto;
import com.kdigital.factoryPick.entity.ComplexRatioEntity;
import com.kdigital.factoryPick.entity.IntegrationEntity;

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
public class ComplexRatioDTO {

    private Integer id;
    private String complexName;
    private Double food;
    private Double clothing;
    private Double wood;
    private Double petrochemical;
    private Double nonMetal;
    private Double metal;
    private Double machinery;
    private Double electronics;
    private Double trans;
    private Double other;
    private Double nonManu;
    
    // Entity -> DTO 변환
    public static ComplexRatioDTO toDTO(ComplexRatioEntity entity) {
        return ComplexRatioDTO.builder()
                .complexName(entity.getComplexName())
                .food(entity.getFood())
                .clothing(entity.getClothing())
                .wood(entity.getWood())
                .petrochemical(entity.getPetrochemical())
                .nonMetal(entity.getNonMetal())
                .metal(entity.getMetal())
                .machinery(entity.getMachinery())
                .electronics(entity.getElectronics())
                .trans(entity.getTrans())
                .other(entity.getOther())
                .nonManu(entity.getNonManu())
                .build();
    }
}
