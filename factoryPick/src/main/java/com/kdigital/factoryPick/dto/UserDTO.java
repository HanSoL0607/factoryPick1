package com.kdigital.factoryPick.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import com.kdigital.factoryPick.entity.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDTO {

    private String userEmail;  // 사용자 이메일
    private String userRole;  // 사용자 역할
    private String userPhone;  // 사용자 휴대폰
    private String userName;  // 사용자 이름
    private String snsType;  // SNS 타입
    private LocalDateTime createdTime;  // 회원 생성 시간
    private LocalDateTime updateTime;  // 회원 정보 업데이트 시간

    // Entity -> DTO 변환 메서드
    public static UserDTO fromEntity(UserEntity entity) {
        return UserDTO.builder()
                .userEmail(entity.getUserEmail())
                .userRole(entity.getUserRole())
                .userPhone(entity.getUserPhone())
                .userName(entity.getUserName())
                .snsType(entity.getSnsType())
                .createdTime(entity.getCreatedTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }

    // DTO -> Entity 변환 메서드
    public static UserEntity toEntity(UserDTO dto) {
        return UserEntity.builder()
                .userEmail(dto.getUserEmail())
                .userRole(dto.getUserRole())
                .userPhone(dto.getUserPhone())
                .userName(dto.getUserName())
                .snsType(dto.getSnsType())
                .createdTime(dto.getCreatedTime())
                .updateTime(dto.getUpdateTime())
                .build();
    }
}
