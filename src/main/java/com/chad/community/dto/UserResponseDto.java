package com.chad.community.dto;

public record UserResponseDto(
    long userId,
    String nickname,
    String profileImage
) {}