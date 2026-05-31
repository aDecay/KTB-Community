package com.chad.community.interceptor;

import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.utils.AuthenticationContextHolder;
import com.chad.community.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader("Authorization");

        // 인증 정보가 없으면 AuthenticationContextHolder가 null
        if (authorization == null) {
            return true;
        }

        // Bearer로 시작하지 않으면 오류
        if (!authorization.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.AUTH_INVALID);
        }

        // userId를 AuthenticationContextHolder에 등록
        String token = authorization.substring(7);
        int userId = jwtUtil.getUserId(token);
        AuthenticationContextHolder.setUserId(userId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // controller 이후 초기화
        AuthenticationContextHolder.clearUserId();
    }
}
