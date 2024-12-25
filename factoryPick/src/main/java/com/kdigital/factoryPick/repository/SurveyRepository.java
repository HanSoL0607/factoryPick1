package com.kdigital.factoryPick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kdigital.factoryPick.entity.SurveyEntity;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {
}
