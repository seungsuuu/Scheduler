package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.LoginRequestDto;
import com.sparta.scheduler.dto.SignupRequestDto;
import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.entity.UserRoleEnum;
import com.sparta.scheduler.jwt.JwtUtil;
import com.sparta.scheduler.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String nickname = requestDto.getNickname();

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, nickname, role);
        userRepository.save(user);
    }

    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("입력한 ID가 틀렸거나, 존재하지 않습니다.")
        );

        if(user.getPassword().equals(password)) {

            String token = jwtUtil.createToken(user.getUsername(), user.getRole());

            res.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        }
    }
}
