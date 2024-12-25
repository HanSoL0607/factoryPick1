package com.kdigital.factoryPick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdigital.factoryPick.entity.LikeListEntity;

public interface LikeListRepository extends JpaRepository<LikeListEntity, Long> {
    List<LikeListEntity> findByUserEmail(String userEmail);
    boolean existsByUserEmailAndComplexName(String userEmail, String complexName);
}