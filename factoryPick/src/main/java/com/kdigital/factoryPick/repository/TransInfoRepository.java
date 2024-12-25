package com.kdigital.factoryPick.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdigital.factoryPick.entity.TransInfoEntity;

public interface TransInfoRepository extends JpaRepository<TransInfoEntity, Long> {
	Optional<TransInfoEntity> findByComplexName(String complexName);  // 산업단지명으로 조회
}
