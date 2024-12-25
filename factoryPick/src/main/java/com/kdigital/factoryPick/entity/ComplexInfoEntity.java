package com.kdigital.factoryPick.entity;

import java.time.LocalDate;

import com.kdigital.factoryPick.dto.ComplexInfoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "complex_info", schema = "factory")
public class ComplexInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // Primary Key

    @Column(name = "complex_name", length = 200)
    private String complexName;  // Foreign Key to complex_basic_pk

    @Column(name = "status", length = 10)
    private String status;  // Status of the complex

    @Column(name = "completion_date", length = 20)
    private String completionDate;  // Completion date

    @Column(name = "avg_price")
    private int avgPrice;  // Average price

    @Column(name = "industry_type", length = 500)
    private String industryType;  // Industry type

    @Column(name = "management", length = 200)
    private String management;  // Management entity

    @Column(name = "land_size")
    private int landSize;  // Land size

    @Column(name = "complex_group", length = 20, nullable = false)
    private String complexGroup;  // Complex group
    
    @Column(name = "website_url", length = 500)
    private String websiteUrl;
    
    // DTO -> Entity 변환 메서드
    public static ComplexInfoEntity toEntity(ComplexInfoDTO dto) {
        return ComplexInfoEntity.builder()
                .id(dto.getId())
                .complexName(dto.getComplexName())
                .status(dto.getStatus())
                .completionDate(dto.getCompletionDate())
                .avgPrice(dto.getAvgPrice())
                .industryType(dto.getIndustryType())
                .management(dto.getManagement())
                .landSize(dto.getLandSize())
                .complexGroup(dto.getComplexGroup())
                .websiteUrl(dto.getWebsiteUrl())
                .build();
    }
}
