package com.chad.community.dto;

import java.time.Instant;

public record PostResponseDto(
        long postId,
        String title,
        String content,
        Instant createdAt,
        int viewCount,
        UserResponseDto writer
) {
}
