package com.kdigital.factoryPick.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdigital.factoryPick.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{
      Optional<UserEntity> findByUserEmail(String userEmail);

}