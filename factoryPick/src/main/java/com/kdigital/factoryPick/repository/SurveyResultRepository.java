package com.kdigital.factoryPick.repository;

import com.kdigital.factoryPick.entity.SurveyResultEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResultRepository extends JpaRepository<SurveyResultEntity, Long> {
    // 가장 최근 10개의 결과를 가져오는 메서드
    List<SurveyResultEntity> findTop10ByOrderByCreateTimeDesc();
    List<SurveyResultEntity> findByUserEmail(String userEmail);
}
