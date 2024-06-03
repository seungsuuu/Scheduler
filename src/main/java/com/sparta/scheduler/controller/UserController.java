package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.LoginRequestDto;
import com.sparta.scheduler.dto.SignupRequestDto;
import com.sparta.scheduler.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {

        userService.signup(requestDto);

        return new ResponseEntity<>("회원 가입이 완료되었습니다.", HttpStatus.valueOf(200));
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {

        userService.login(requestDto, res);

        return new ResponseEntity<>("로그인이 완료되었습니다.", HttpStatus.valueOf(200));
    }
}
