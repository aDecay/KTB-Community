package com.chad.community.mapper;

import com.chad.community.dto.AuthResponseDto;

public class AuthMapper {
    public static AuthResponseDto mapTokenToAuthResponse(String token) {
        return new AuthResponseDto(token);
    }
}
