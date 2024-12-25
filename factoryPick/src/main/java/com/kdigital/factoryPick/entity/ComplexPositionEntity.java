package com.kdigital.factoryPick.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.kdigital.factoryPick.dto.ComplexPositionDTO;

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
@Table(name = "complex_position")
public class ComplexPositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // AUTO_INCREMENT
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "complex_name", length = 200, nullable = false)
    private String complexName;

    @Column(name = "latitude", length = 100, nullable = false)
    private String latitude;

    @Column(name = "longitude", length = 100, nullable = false)
    private String longitude;

    // DTO -> Entity 변환
    public static ComplexPositionEntity toEntity(ComplexPositionDTO dto) {
        return ComplexPositionEntity.builder()
                .complexName(dto.getComplexName())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}

