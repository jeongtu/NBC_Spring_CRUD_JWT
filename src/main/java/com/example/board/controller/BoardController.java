package com.example.board.controller;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.createBoard(boardRequestDto,request);
    }

    @GetMapping
    public List<BoardResponseDto> getBoard() {
        return boardService.getBoards();
    }

    @GetMapping("/{id}")
    public BoardResponseDto getBoardOne(@PathVariable Long id) {
        return boardService.getBoardOne(id);
    }

    @PutMapping("/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        return boardService.updateBoard(id,boardRequestDto,request);
    }

    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id,request);
    }
}