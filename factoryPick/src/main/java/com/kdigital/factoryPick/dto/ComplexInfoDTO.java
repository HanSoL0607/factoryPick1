package com.kdigital.factoryPick.dto;

import java.time.LocalDate;

import com.kdigital.factoryPick.entity.ComplexInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexInfoDTO {
    private Long id;
    private String complexName;
    private String status;
    private String completionDate;
    private int avgPrice;
    private String industryType;
    private String management;
    private int landSize;
    private String complexGroup;
    private String WebsiteUrl;

    // Entity -> DTO 변환 메서드
    public static ComplexInfoDTO toDTO(ComplexInfoEntity entity) {
        return ComplexInfoDTO.builder()
                .id(entity.getId())
                .complexName(entity.getComplexName())
                .status(entity.getStatus())
                .completionDate(entity.getCompletionDate())
                .avgPrice(entity.getAvgPrice())
                .industryType(entity.getIndustryType())
                .management(entity.getManagement())
                .landSize(entity.getLandSize())
                .complexGroup(entity.getComplexGroup())
                .WebsiteUrl(entity.getWebsiteUrl())
                .build();
    }
}

