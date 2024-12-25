package com.kdigital.factoryPick.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RecommendationSurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String region;           // 지역
    private String transportation;   // 교통수단
    private int landPrice;           // 최대 토지 지가
    private String industry;         // 업종
    private String product;          // 생산품
    private String rawMaterial;      // 원자재

    // Constructor to initialize fields using DTO
    public RecommendationSurveyEntity(String region, String transportation, int landPrice, 
                                      String industry, String product, String rawMaterial) {
        this.region = region;
        this.transportation = transportation;
        this.landPrice = landPrice;
        this.industry = industry;
        this.product = product;
        this.rawMaterial = rawMaterial;
    }
}

