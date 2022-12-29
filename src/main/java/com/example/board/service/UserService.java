package com.example.board.service;

import com.example.board.dto.SignInRequestDto;
import com.example.board.dto.SignUpRequestDto;
import com.example.board.entity.User;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String signUp(SignUpRequestDto signUpRequestDto) {
        String username = signUpRequestDto.getUsername();
        String password = signUpRequestDto.getPassword();

        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()){
            throw new IllegalArgumentException("유저명이 중복입니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);

        return "회원가입 완료";
    }

    @Transactional
    public String signIn(SignInRequestDto signInRequestDto, HttpServletResponse response) {
        String username = signInRequestDto.getUsername();
        String password = signInRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 이름으로 등록한 유저가 없습니다.")
        );

        if(!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(),user.getRole()));

        return "로그인 완료";
    }
}
