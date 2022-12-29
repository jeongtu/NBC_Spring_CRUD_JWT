package com.example.board.controller;

import com.example.board.dto.SignInRequestDto;
import com.example.board.dto.SignUpRequestDto;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        return userService.signUp(signUpRequestDto);
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody SignInRequestDto signInRequestDto, HttpServletResponse response){
        return userService.signIn(signInRequestDto,response);

    }


}
