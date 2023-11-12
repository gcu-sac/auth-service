package com.example.justJwt.jwt.interceptor;



import com.example.justJwt.jwt.service.JwtServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import java.util.Objects;


@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtServiceImpl jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if(request.getMethod().equals("OPTIONS")) { // preflight로 넘어온 options는 통과
            return true;
        }
        else {
            String token = request.getHeader("jwt-auth-token"); // 헤더에서 토큰 받아옴
            String cookieToken = Objects.requireNonNull(WebUtils.getCookie(request, "jwtAuthToken")).getValue();

            if (cookieToken != null && cookieToken.length() > 0) {
                jwtService.checkValid(cookieToken); // 토큰 유효성 검증
                return true;
            }
            else if (token != null && token.length() > 0) {
                jwtService.checkValid(token); // 토큰 유효성 검증
                return true;
            }
            else { // 유효한 인증토큰이 아닐 경우
                throw new Exception("유효한 인증토큰이 존재하지 않습니다.");
            }
        }
    }
}
