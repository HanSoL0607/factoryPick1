package com.kdigital.factoryPick.service;

import java.util.Optional;


import org.springframework.stereotype.Service;

import com.kdigital.factoryPick.dto.ComplexRatioDTO;
import com.kdigital.factoryPick.entity.ComplexRatioEntity;
import com.kdigital.factoryPick.repository.ComplexRatioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplexRatioService {

    private final ComplexRatioRepository repository;

    public ComplexRatioDTO getComplexRatioByComplexName(String complexName) {
        Optional<ComplexRatioEntity> complexRatio = repository.findByComplexName(complexName);

        if (complexRatio.isPresent()) {
        	ComplexRatioEntity entity = complexRatio.get();
            return ComplexRatioDTO.toDTO(entity);  // Entity -> DTO 변환
        } 
        // 예외 처리: 찾을 수 없는 경우
        throw new RuntimeException("업종 집적도 정보를 찾을 수 없습니다: " + complexName);
    }
}