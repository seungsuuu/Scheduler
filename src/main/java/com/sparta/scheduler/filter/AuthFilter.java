package com.sparta.scheduler.filter;


import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.jwt.JwtUtil;
import com.sparta.scheduler.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) && url.startsWith("/api/user")) {
            log.info("인증처리를 하지 않는 URL : " + url);
            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // Header 에 있는 JWT 토큰 가져와서 substring
            String token = jwtUtil.getJwtFromHeader(httpServletRequest);

            if (StringUtils.hasText(token)) { // 토큰이 존재하면 검증 시작

                // 토큰 검증
                if (!jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                        new NullPointerException("Not Found User")
                );

                httpServletRequest.setAttribute("user", user);

                chain.doFilter(httpServletRequest, httpServletResponse); // 검증 완료되면 다음 Filter 로 이동
            } else {
                throw new IllegalArgumentException("인증된 사용자가 아닙니다! 로그인 해주세요.");
            }
        }
    }

}
