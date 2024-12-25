package com.kdigital.factoryPick.repository;

import com.kdigital.factoryPick.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
   
}
