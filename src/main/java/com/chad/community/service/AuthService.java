package com.chad.community.service;

import com.chad.community.dto.AuthRequestDto;
import com.chad.community.dto.AuthResponseDto;
import com.chad.community.entity.User;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.mapper.AuthMapper;
import com.chad.community.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthResponseDto createToken(AuthRequestDto authRequestDto) {
        try {
            User user = userService.findUserByEmailAndPassword(authRequestDto.email(), authRequestDto.password());

            String token = jwtUtil.createToken(user.getId());

            return AuthMapper.mapTokenToAuthResponse(token);
        } catch (CustomException e) {
            if (e.getErrorCode() == ErrorCode.USER_NOT_FOUND) {
                throw new CustomException(ErrorCode.AUTH_FAILED);
            }

            throw e;
        }
    }
}
