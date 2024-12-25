package com.kdigital.factoryPick.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import com.kdigital.factoryPick.dto.UserDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "users")  // 테이블 명은 'users'
public class UserEntity {

    @Id
    @Column(name = "user_email", length = 255, nullable = false)
    private String userEmail;  // 사용자 이메일 (Primary Key)

    @Column(name = "user_role", length = 20, nullable = false)
    private String userRole = "ROLE_USER";  // 사용자 역할

    @Column(name = "user_phone", length = 50, nullable = false)
    private String userPhone;  // 사용자 휴대폰

    @Column(name = "user_name", length = 100)
    private String userName;  // 사용자 이름

    @Column(name = "sns_type", length = 50, nullable = false)
    private String snsType;  // SNS 타입

    @Column(name = "created_time", nullable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdTime;  // 회원 생성 시간

    @Column(name = "update_time")
    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime updateTime;  // 회원 정보 업데이트 시간
    
    @Column(name = "provider_id", length = 100, nullable = false)
    private String providerId;  // OAuth2 제공자 ID

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
    public String getPrincipalName() {
        return this.userEmail;  // 사용자 이메일을 principalName으로 사용
    }
    
}