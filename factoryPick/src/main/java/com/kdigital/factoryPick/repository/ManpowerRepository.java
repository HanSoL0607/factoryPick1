package com.kdigital.factoryPick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kdigital.factoryPick.entity.ManpowerEntity;

public interface ManpowerRepository extends JpaRepository<ManpowerEntity, Long> {
}
