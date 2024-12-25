package com.kdigital.factoryPick.entity;

import com.kdigital.factoryPick.dto.ComplexBasicDTO;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "complex_basic_factory")
public class ComplexBasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // Primary Key

    @Column(name = "complex_name", nullable = false, length = 200)
    private String complexName;  // Foreign Key to complex_basic_pk

    @Column(name = "address", length = 200)
    private String address;  // Complex address

    @Column(name = "main_industry")
    private int mainIndustry;  // Main Industry
    
    // Entity -> DTO 변환 메서드
    public static ComplexBasicDTO fromEntity(ComplexBasicEntity entity) {
        return ComplexBasicDTO.builder()
                .id(entity.getId())
                .complexName(entity.getComplexName())
                .address(entity.getAddress())
                .mainIndustry(entity.getMainIndustry())
                .build();
    }
}
