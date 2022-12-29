package com.example.board.dto;


import com.example.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {

    private String title;
    private String content;

    public Board toEntity(String username) {
        return new Board(this.title, username, this.content);
    }

}