package com.chad.community.resolver;

import com.chad.community.annotation.AuthenticationParameter;
import com.chad.community.dto.AuthenticationInfo;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticationParameter.class) != null
                && parameter.getParameterType().equals(AuthenticationInfo.class);
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader("Authorization");

        // 인증 정보가 없으면 null
        if (authorization == null) {
            return null;
        }

        // Bearer로 시작하지 않으면 오류
        if (!authorization.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.AUTH_INVALID);
        }

        // AuthenticationInfo 생성
        String token = authorization.substring(7);
        int userId = jwtUtil.getUserId(token);

        return new AuthenticationInfo(userId);
    }
}
