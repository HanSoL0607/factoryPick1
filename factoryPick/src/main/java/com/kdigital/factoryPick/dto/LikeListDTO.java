package com.kdigital.factoryPick.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LikeListDTO {

    private Integer id;
    private String userEmail;
    private String complexName;
    private LocalDateTime createdLikeTime;
}

