package com.kdigital.factoryPick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kdigital.factoryPick.entity.ComplexTransEntity;

public interface ComplexTransRepository extends JpaRepository<ComplexTransEntity, Long> {
	
}
