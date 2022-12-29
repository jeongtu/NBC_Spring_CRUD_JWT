package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String username;
    private String content;
    private LocalDateTime createdAt;


    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();

    }
}