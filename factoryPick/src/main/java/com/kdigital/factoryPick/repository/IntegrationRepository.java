package com.kdigital.factoryPick.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdigital.factoryPick.entity.IntegrationEntity;

public interface IntegrationRepository extends JpaRepository<IntegrationEntity, Long> {
	 // 산업단지 이름으로 데이터를 조회하는 메서드
    Optional<IntegrationEntity> findByComplexName(String complexName);
}
