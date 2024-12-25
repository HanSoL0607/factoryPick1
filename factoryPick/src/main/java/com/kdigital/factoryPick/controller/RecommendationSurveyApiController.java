package com.kdigital.factoryPick.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdigital.factoryPick.dto.SurveyResultWithPositionDTO;
import com.kdigital.factoryPick.entity.ComplexPositionEntity;
import com.kdigital.factoryPick.entity.SurveyResultEntity;
import com.kdigital.factoryPick.repository.ComplexPositionRepository;
import com.kdigital.factoryPick.repository.SurveyResultRepository;

@RestController
@RequestMapping("/recommendation/api")
public class RecommendationSurveyApiController {

    @Autowired
    private SurveyResultRepository surveyResultRepository;

    @Autowired
    private ComplexPositionRepository complexPositionRepository;

    // 최근 10개의 설문 조사 결과와 각 단지의 위치 정보를 "JSON"으로 반환
    @GetMapping("/recent-results")
    public List<SurveyResultWithPositionDTO> getRecentResults() {
        List<SurveyResultEntity> surveyResults = surveyResultRepository.findTop10ByOrderByCreateTimeDesc();
        List<SurveyResultWithPositionDTO> resultWithPosition = new ArrayList<>();

        // SurveyResultEntity 순회하며 complex_name에 해당하는 위치 정보(위도, 경도)를 가져와서 DTO로 변환
        for (SurveyResultEntity surveyResult : surveyResults) {
            ComplexPositionEntity position;
            try {
                // complex_name으로 위치 정보를 찾음
                position = complexPositionRepository.findByComplexName(surveyResult.getComplexName())
                        .orElseThrow(() -> new RuntimeException("해당 단지의 위치 정보를 찾을 수 없습니다: " + surveyResult.getComplexName()));
            } catch (RuntimeException e) {
                // 위치 정보를 찾지 못하면 기본 값으로 설정 // 현재 오류 없음
                position = ComplexPositionEntity.builder()
                        .complexName(surveyResult.getComplexName())
                        .latitude("0")   // 예외 발생 시 위도 0
                        .longitude("0")  // 예외 발생 시 경도 0
                        .build();
            }

            // SurveyResultEntity와 ComplexPositionEntity를 결합한 DTO 생성
            SurveyResultWithPositionDTO dto = new SurveyResultWithPositionDTO(surveyResult, position);
            resultWithPosition.add(dto);
        }

        return resultWithPosition;
    }
}
