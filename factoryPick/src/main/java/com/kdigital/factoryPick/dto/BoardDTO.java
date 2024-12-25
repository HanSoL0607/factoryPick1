package com.kdigital.factoryPick.dto;

import com.kdigital.factoryPick.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long boardId;
    private String userEmail;
    private String boardTitle;
    private String boardContents;
    private String category;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;

    // Entity -> DTO 변환 메서드
    public static BoardDTO fromEntity(BoardEntity entity) {
        return BoardDTO.builder()
                .boardId(entity.getBoardId())
                .userEmail(entity.getUserEmail())
                .boardTitle(entity.getBoardTitle())
                .boardContents(entity.getBoardContents())
                .category(entity.getCategory())
                .createdTime(entity.getCreatedTime())
                .updateTime(entity.getUpdateTime())
                .build();
    }

    // DTO -> Entity 변환 메서드
    public static BoardEntity toEntity(BoardDTO dto) {
        return BoardEntity.builder()
                .boardId(dto.getBoardId())
                .userEmail(dto.getUserEmail())
                .boardTitle(dto.getBoardTitle())
                .boardContents(dto.getBoardContents())
                .category(dto.getCategory())
                .createdTime(dto.getCreatedTime())
                .updateTime(dto.getUpdateTime())
                .build();
    }
}
