package com.kdigital.factoryPick.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kdigital.factoryPick.dto.ComplexInfoDTO;
import com.kdigital.factoryPick.entity.ComplexInfoEntity;
import com.kdigital.factoryPick.repository.ComplexInfoRepository;

@Service
public class ComplexInfoService {

    private final ComplexInfoRepository repository;


    public ComplexInfoService(ComplexInfoRepository repository) {
        this.repository = repository;
    }

    public ComplexInfoDTO getComplexInfoByName(String complexName) {
    	Optional<ComplexInfoEntity> complexInfo = repository.findByComplexName(complexName);

    	ComplexInfoDTO dto = null;
    	if(complexInfo.isPresent()) {
    		ComplexInfoEntity entity = complexInfo.get();
    		dto = ComplexInfoDTO.toDTO(entity);
    	}
        
        return dto;
    }
}
