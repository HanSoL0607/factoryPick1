package com.kdigital.factoryPick.dto;

import com.kdigital.factoryPick.entity.ComplexBasicPkEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexBasicPkDTO {
    private String complexName;
    private String province;
    private int landPrice;
    private String complexGroup;

    // Entity -> DTO 변환 메서드
    public static ComplexBasicPkDTO fromEntity(ComplexBasicPkEntity entity) {
        return ComplexBasicPkDTO.builder()
                .complexName(entity.getComplexName())
                .province(entity.getProvince())
                .landPrice(entity.getLandPrice())
                .complexGroup(entity.getComplexGroup())
                .build();
    }

    // DTO -> Entity 변환 메서드
    public static ComplexBasicPkEntity toEntity(ComplexBasicPkDTO dto) {
        return ComplexBasicPkEntity.builder()
                .complexName(dto.getComplexName())
                .province(dto.getProvince())
                .landPrice(dto.getLandPrice())
                .complexGroup(dto.getComplexGroup())
                .build();
    }
}
