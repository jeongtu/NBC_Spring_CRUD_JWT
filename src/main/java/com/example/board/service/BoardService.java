package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.entity.Board;
import com.example.board.entity.User;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 게시글 작성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {
        // 1. request에서 토큰 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 2. 토큰이 있으면 게시글 작성
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰값이 잘못되었습니다.");
            }


            // 3. 토큰에서 가져온 사용자 정보로 DB에서 찾아 사용할 유저 객체 생성
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
            );

            Board board = boardRequestDto.toEntity(user.getUsername());
            boardRepository.save(board);
            return new BoardResponseDto(board);
        } else {
            return null;
        }

    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoards() {
        List<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> lists = new ArrayList<>();

        for (Board board : boards) {
            lists.add(new BoardResponseDto(board));
        }
        return lists;
    }

    @Transactional(readOnly = true)
    public BoardResponseDto getBoardOne(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않음")
        );
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰값이 잘못되었습니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
            );

            if(board.getUsername().equals(user.getUsername())) {
                board.update(boardRequestDto.getTitle(), boardRequestDto.getContent());
            } else throw new RuntimeException("본인이 작성한 게시글만 수정할 수 있습니다.");
        } else {
            return null;
        }
        return new BoardResponseDto(board);

    }

    @Transactional
    public String deleteBoard(Long id, HttpServletRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 id의 포스트가 존재하지 않습니다.")
        );

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if(jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰값이 잘못되었습니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
            );

            if (board.getUsername().equals(user.getUsername())) {
                boardRepository.deleteById(id);
            } else throw new RuntimeException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        } else {
            return null;
        }
        return "게시글 삭제 성공";
    }
}


