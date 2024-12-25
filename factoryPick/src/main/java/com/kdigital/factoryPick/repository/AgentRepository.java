package com.kdigital.factoryPick.repository;

import com.kdigital.factoryPick.entity.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
    
}
