package com.kdigital.factoryPick.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kdigital.factoryPick.dto.TransInfoDTO;
import com.kdigital.factoryPick.entity.TransInfoEntity;
import com.kdigital.factoryPick.repository.TransInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransInfoService {

    private final TransInfoRepository repository;

    public TransInfoDTO getComplexInfoByName(String complexName) {
    	Optional<TransInfoEntity> transInfo = repository.findByComplexName(complexName);

    	TransInfoDTO dto = null;
    	if(transInfo.isPresent()) {
    		TransInfoEntity entity = transInfo.get();
    		dto = TransInfoDTO.toDTO(entity);
    	}
        
        return dto;
    }
}

