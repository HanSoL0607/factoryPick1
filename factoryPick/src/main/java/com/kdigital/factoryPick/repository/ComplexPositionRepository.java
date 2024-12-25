package com.kdigital.factoryPick.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kdigital.factoryPick.entity.ComplexPositionEntity;

public interface ComplexPositionRepository extends JpaRepository<ComplexPositionEntity, Long> {
    Optional<ComplexPositionEntity> findByComplexName(String complexName);
}

