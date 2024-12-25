package com.kdigital.factoryPick.repository;

import com.kdigital.factoryPick.entity.ComplexBasicPkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplexBasicPKRepository extends JpaRepository<ComplexBasicPkEntity, String> {
   
}
