package com.kdigital.factoryPick.repository;

import com.kdigital.factoryPick.entity.RecommendationSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationSurveyRepository extends JpaRepository<RecommendationSurveyEntity, Long> {
}

