package com.kdigital.factoryPick.dto;

import com.kdigital.factoryPick.entity.ComplexPositionEntity;
import com.kdigital.factoryPick.entity.SurveyResultEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResultWithPositionDTO {

    private String complexName;
    private double regionScore;
    private double industryScore;
    private double landPriceScore;
    private double rawMaterialScore;
    private double productScore;
    private double transportScore;
    private double workerScore;
    private double finalSimilarityScore;
    private String latitude;   // 위도
    private String longitude;  // 경도
    private Integer rankResult;

    // SurveyResultEntity와 ComplexPositionEntity를 기반으로 DTO 생성하는 방법
    public SurveyResultWithPositionDTO(SurveyResultEntity surveyResult, ComplexPositionEntity position) {
        this.complexName = surveyResult.getComplexName();
        this.regionScore = surveyResult.getRegionScore();
        this.industryScore = surveyResult.getIndustryScore();
        this.landPriceScore = surveyResult.getLandPriceScore();
        this.rawMaterialScore = surveyResult.getRawMaterialScore();
        this.productScore = surveyResult.getProductScore();
        this.transportScore = surveyResult.getTransportScore();
        this.workerScore = surveyResult.getWorkerScore();
        this.finalSimilarityScore = surveyResult.getFinalSimilarityScore();
        this.latitude = position.getLatitude();
        this.longitude = position.getLongitude();
        this.rankResult = surveyResult.getRankResult();
    }
}
