package com.kdigital.factoryPick.dto;

import com.kdigital.factoryPick.entity.SurveyEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//SurveyDTO.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SurveyDTO {

    private Long id;  // Long으로 변경
    private String userEmail;
    private String provinceInput;
    private Integer highwayInput;
    private Integer portInput;
    private Integer trainInput;
    private Integer airportInput;
    private Integer priceInput;
    private Double industryInput;
    private String productsInput;
    private String rawMaterialsInput;

    // Entity -> DTO 변환 메서드
    public static SurveyDTO fromEntity(SurveyEntity entity) {
        return SurveyDTO.builder()
                .id(entity.getId())
                .userEmail(entity.getUserEmail())
                .provinceInput(entity.getProvinceInput())
                .highwayInput(entity.getHighwayInput())
                .portInput(entity.getPortInput())
                .trainInput(entity.getTrainInput())
                .airportInput(entity.getAirportInput())
                .priceInput(entity.getPriceInput())
                .industryInput(entity.getIndustryInput())
                .productsInput(entity.getProductsInput())
                .rawMaterialsInput(entity.getRawMaterialsInput())
                .build();
    }

    // DTO -> Entity 변환 메서드
    public static SurveyEntity toEntity(SurveyDTO dto) {
        return SurveyEntity.builder()
                .userEmail(dto.getUserEmail())
                .provinceInput(dto.getProvinceInput())
                .highwayInput(dto.getHighwayInput())
                .portInput(dto.getPortInput())
                .trainInput(dto.getTrainInput())
                .airportInput(dto.getAirportInput())
                .priceInput(dto.getPriceInput())
                .industryInput(dto.getIndustryInput())
                .productsInput(dto.getProductsInput())
                .rawMaterialsInput(dto.getRawMaterialsInput())
                .build();
    }
}