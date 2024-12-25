package com.kdigital.factoryPick.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kdigital.factoryPick.entity.ComplexInfoEntity;

public interface ComplexInfoRepository extends JpaRepository<ComplexInfoEntity, Long> {
    Optional<ComplexInfoEntity> findByComplexName(String complexName);  // 산업단지명으로 조회
}
