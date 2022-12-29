package com.example.board.dto;

import lombok.Getter;

@Getter
public class ResMessageStatusCodeDto {

    private String message;
    private int statusCode;

    public ResMessageStatusCodeDto(String message, int statusCode) {
        this.message = message;
        this.statusCode= statusCode;
    }
}
