package com.kdigital.factoryPick.dto;

import com.kdigital.factoryPick.entity.ManpowerEntity;
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
public class ManpowerDTO {

    private Long id;  // Long으로 변경
    private String complexName;
    private Double manpower;

    // Entity -> DTO 변환 메서드
    public static ManpowerDTO fromEntity(ManpowerEntity entity) {
        return ManpowerDTO.builder()
                .id(entity.getId())
                .complexName(entity.getComplexName())
                .manpower(entity.getManpower())
                .build();
    }

    // DTO -> Entity 변환 메서드
    public static ManpowerEntity toEntity(ManpowerDTO dto) {
        return ManpowerEntity.builder()
                .complexName(dto.getComplexName())
                .manpower(dto.getManpower())
                .build();
    }
}
