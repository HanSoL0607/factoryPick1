package com.kdigital.factoryPick.repository;

import com.kdigital.factoryPick.entity.ComplexBasicEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexBasicRepository extends JpaRepository<ComplexBasicEntity, Long> {
    // 여러 complex_name에 해당하는 데이터를 조회
    List<ComplexBasicEntity> findByComplexNameIn(List<String> complexNames);
}
