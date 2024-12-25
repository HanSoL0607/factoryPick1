package com.kdigital.factoryPick.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;  // Primary Key

    @Column(name = "user_email", nullable = false, length = 255)
    private String userEmail;  // Foreign Key to users

    @Column(name = "board_title", length = 255, nullable = false)
    private String boardTitle;  // Board Title

    @Column(name = "board_contents", columnDefinition = "TEXT")
    private String boardContents;  // Board Contents

    @Column(name = "category", length = 100)
    private String category;  // Board Category

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;  // Creation Time

    @Column(name = "update_time")
    private LocalDateTime updateTime;  // Update Time
}
