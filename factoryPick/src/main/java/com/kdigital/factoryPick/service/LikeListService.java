package com.kdigital.factoryPick.service;

import java.time.LocalDateTime;
import java.util.List;


import com.kdigital.factoryPick.entity.LikeListEntity;
import com.kdigital.factoryPick.repository.LikeListRepository;

import org.springframework.stereotype.Service;

@Service
public class LikeListService {
    private final LikeListRepository likeListRepository;

    public LikeListService(LikeListRepository likeListRepository) {
        this.likeListRepository = likeListRepository;
    }

    // 이메일로 complex_name 리스트 반환
    public List<String> getComplexNamesByUserEmail(String userEmail) {
        List<LikeListEntity> likeListEntities = likeListRepository.findByUserEmail(userEmail);
        return likeListEntities.stream()
                .map(LikeListEntity::getComplexName)  // 각 엔티티에서 complexName 추출
                .toList();  // 결과를 리스트로 변환
    }
    
    public void saveLike(String userEmail, String complexName) {
        LikeListEntity like = new LikeListEntity(null, userEmail, complexName, LocalDateTime.now());
        likeListRepository.save(like);
    }
    // 사용자가 특정 complexName을 이미 찜했는지 확인
    public boolean isLikedByUser(String userEmail, String complexName) {
        return likeListRepository.existsByUserEmailAndComplexName(userEmail, complexName);
    }
}
