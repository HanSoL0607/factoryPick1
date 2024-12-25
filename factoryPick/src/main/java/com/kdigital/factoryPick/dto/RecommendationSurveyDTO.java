package com.kdigital.factoryPick.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data	// getter, setter, toString, equals, hashcode 메서드 생성
@AllArgsConstructor	// 모든 필드 매개변수 생성자
@NoArgsConstructor	// 기본 생성자
public class RecommendationSurveyDTO {
    private String region;      	 // 지역
    private TransportationPreferenceDTO transportation;   // 교통수단
    private int landPrice;     		 // 최대 토지 지가
    private String industry;     	 // 업종
    private String product;      	 // 생산품
    private String rawMaterial;  	 // 원자재
    
}