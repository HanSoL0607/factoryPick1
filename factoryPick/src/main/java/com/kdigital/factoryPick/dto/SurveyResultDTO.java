package com.kdigital.factoryPick.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import com.kdigital.factoryPick.entity.SurveyResultEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SurveyResultDTO {

    private Long id;
    private String userEmail;  // 사용자 이메일 (테스트용 가상 이메일 사용 가능)
    private String complexName;  // 단지명
    private Double regionScore;  // 시도 유사도
    private Double industryScore;  // 업종 집적도
    private Double landPriceScore;  // 토지 개별공시지가 점수
    private Double rawMaterialScore;  // 원자재 유사도
    private Double productScore;  // 생산품 유사도
    private Double transportScore;  // 교통 점수
    private Double workerScore;  // 노동력 점수
    private LocalDateTime createTime;  // 생성 시간
    private Integer rankResult;  // 순위
    private Double finalSimilarityScore;  // 최종 유사도

    // Entity -> DTO 변환 메서드
    public static SurveyResultDTO fromEntity(SurveyResultEntity entity) {
        return SurveyResultDTO.builder()
                .id(entity.getId())
                .userEmail(entity.getUserEmail())
                .complexName(entity.getComplexName())
                .regionScore(entity.getRegionScore())
                .industryScore(entity.getIndustryScore())
                .landPriceScore(entity.getLandPriceScore())
                .rawMaterialScore(entity.getRawMaterialScore())
                .productScore(entity.getProductScore())
                .transportScore(entity.getTransportScore())
                .workerScore(entity.getWorkerScore())
                .createTime(entity.getCreateTime())
                .rankResult(entity.getRankResult())
                .finalSimilarityScore(entity.getFinalSimilarityScore())
                .build();
    }

    // DTO -> Entity 변환 메서드
    public static SurveyResultEntity toEntity(SurveyResultDTO dto) {
        return SurveyResultEntity.builder()
                .id(dto.getId())
                .userEmail(dto.getUserEmail())
                .complexName(dto.getComplexName())
                .regionScore(dto.getRegionScore())
                .industryScore(dto.getIndustryScore())
                .landPriceScore(dto.getLandPriceScore())
                .rawMaterialScore(dto.getRawMaterialScore())
                .productScore(dto.getProductScore())
                .transportScore(dto.getTransportScore())
                .workerScore(dto.getWorkerScore())
                .createTime(dto.getCreateTime())
                .rankResult(dto.getRankResult())
                .finalSimilarityScore(dto.getFinalSimilarityScore())
                .build();
    }
}

