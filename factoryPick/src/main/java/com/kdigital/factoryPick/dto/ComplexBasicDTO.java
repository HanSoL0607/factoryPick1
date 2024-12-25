package com.kdigital.factoryPick.dto;

import com.kdigital.factoryPick.entity.ComplexBasicEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexBasicDTO {
    private Long id;
    private String complexName;
    private String address;
    private int mainIndustry;

    // Entity -> DTO 변환 메서드
    public static ComplexBasicDTO fromEntity(ComplexBasicEntity entity) {
        return ComplexBasicDTO.builder()
                .id(entity.getId())
                .complexName(entity.getComplexName())
                .address(entity.getAddress())
                .mainIndustry(entity.getMainIndustry())
                .build();
    }

    // DTO -> Entity 변환 메서드
    public static ComplexBasicEntity toEntity(ComplexBasicDTO dto) {
        return ComplexBasicEntity.builder()
                .id(dto.getId())
                .complexName(dto.getComplexName())
                .address(dto.getAddress())
                .mainIndustry(dto.getMainIndustry())
                .build();
    }
}
