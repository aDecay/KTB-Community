package com.chad.community.dto;

public record UserResponseDto(
    int userId,
    String nickname,
    String profileImage
) {}