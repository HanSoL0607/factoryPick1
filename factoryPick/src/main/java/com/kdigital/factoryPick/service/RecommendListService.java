package com.kdigital.factoryPick.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kdigital.factoryPick.dto.ComplexBasicDTO;
import com.kdigital.factoryPick.entity.ComplexBasicEntity;
import com.kdigital.factoryPick.entity.SurveyResultEntity;
import com.kdigital.factoryPick.repository.ComplexBasicRepository;
import com.kdigital.factoryPick.repository.SurveyResultRepository;

@Service
public class RecommendListService {
    private final SurveyResultRepository surveyResultRepository;
    private final ComplexBasicRepository complexBasicRepository;

    // 생성자 주입
    public RecommendListService(SurveyResultRepository surveyResultRepository,
                                ComplexBasicRepository complexBasicRepository) {
        this.surveyResultRepository = surveyResultRepository;
        this.complexBasicRepository = complexBasicRepository;
    }

    // 이메일로 complex_name 리스트 반환
    public List<String> getComplexNamesByUserEmail(String userEmail) {
        List<SurveyResultEntity> surveyResultEntities = surveyResultRepository.findByUserEmail(userEmail);
        return surveyResultEntities.stream()
                .map(SurveyResultEntity::getComplexName)  // 각 엔티티에서 complexName 추출
                .distinct()  // 중복 제거
                .collect(Collectors.toList());
    }

    // 대용량 complex_name에 대한 주소 리스트 반환
    public List<ComplexBasicDTO> getComplexAddressesByNames(List<String> complexNames) {
        // 중복 제거 및 공백 제거
        Set<String> uniqueNames = complexNames.stream()
                .map(String::trim)        // 앞뒤 공백 제거
                .map(String::toLowerCase) // 소문자로 변환
                .collect(Collectors.toSet()); // Set으로 중복 제거

        List<ComplexBasicDTO> result = new ArrayList<>();

        // 10개씩 쿼리를 나누어 실행 (대용량 처리)
        List<String> uniqueNameList = new ArrayList<>(uniqueNames);
        for (int i = 0; i < uniqueNameList.size(); i += 10) {
            List<String> subList = uniqueNameList.subList(i, Math.min(i + 10, uniqueNameList.size()));
            List<ComplexBasicEntity> entities = complexBasicRepository.findByComplexNameIn(subList);

            // 매칭된 이름들 추출
            Set<String> matchedNames = entities.stream()
                    .map(ComplexBasicEntity::getComplexName)
                    .map(String::toLowerCase) // 소문자 변환 (일관성 유지)
                    .collect(Collectors.toSet());

            // 매칭되지 않은 이름들 로그 출력
            subList.stream()
                    .filter(name -> !matchedNames.contains(name.toLowerCase()))
                    .forEach(name -> System.out.println("매칭되지 않은 이름: " + name));

            // 매칭된 엔티티들을 DTO로 변환하여 결과에 추가
            result.addAll(entities.stream()
                    .map(ComplexBasicEntity::fromEntity)
                    .collect(Collectors.toList()));
        }

        return result;
    }
}

