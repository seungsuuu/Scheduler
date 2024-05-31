package com.sparta.scheduler.controller;

import com.sparta.scheduler.dto.LoginRequestDto;
import com.sparta.scheduler.dto.SignupRequestDto;
import com.sparta.scheduler.entity.UserRoleEnum;
import com.sparta.scheduler.jwt.JwtUtil;
import com.sparta.scheduler.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public UserController(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
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

    // JWT 생성 및 저장
    @GetMapping("/create-jwt")
    public String createJwt(HttpServletResponse res) {
        // Jwt 생성
        String token = jwtUtil.createToken("Robbie", UserRoleEnum.USER);

        // Jwt 쿠키 저장
        res.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return token;
    }

    // JWT 가져오기
    @GetMapping("/get-jwt")
    public String getJwt(HttpServletRequest request) {
        // Header 에 있는 JWT 토큰 가져와서 substring
        String token = jwtUtil.getJwtFromHeader(request);

        // 토큰 검증
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        String username = info.getSubject();
        System.out.println("username = " + username);
        // 사용자 권한
        String authority = (String) info.get(JwtUtil.AUTHORIZATION_KEY);
        System.out.println("authority = " + authority);

        return "getJwt : " + username + ", " + authority;
    }
}
