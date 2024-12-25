package com.kdigital.factoryPick.service;

import java.util.Optional;


import org.springframework.stereotype.Service;

import com.kdigital.factoryPick.dto.IntegrationDTO;
import com.kdigital.factoryPick.entity.IntegrationEntity;
import com.kdigital.factoryPick.repository.IntegrationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final IntegrationRepository repository;

    public IntegrationDTO getIntegrationByComplexName(String complexName) {
        Optional<IntegrationEntity> integration = repository.findByComplexName(complexName);

        if (integration.isPresent()) {
            IntegrationEntity entity = integration.get();
            return IntegrationDTO.toDTO(entity);  // Entity -> DTO 변환
        } 
        // 예외 처리: 찾을 수 없는 경우
        throw new RuntimeException("업종 집적도 정보를 찾을 수 없습니다: " + complexName);
    }
}