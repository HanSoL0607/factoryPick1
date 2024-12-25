package com.kdigital.factoryPick.entity;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.kdigital.factoryPick.dto.LikeListDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "like_list")
public class LikeListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_email", length = 255, nullable = false)
    private String userEmail;

    @Column(name = "complex_name", length = 200, nullable = false)
    private String complexName;

    @Column(name = "created_like_time", nullable = false)
    private java.time.LocalDateTime createdLikeTime;

    // DTO -> Entity 변환
    public static LikeListEntity toEntity(LikeListDTO dto) {
        return LikeListEntity.builder()
                .userEmail(dto.getUserEmail())
                .complexName(dto.getComplexName())
                .createdLikeTime(dto.getCreatedLikeTime())
                .build();
    }
}
