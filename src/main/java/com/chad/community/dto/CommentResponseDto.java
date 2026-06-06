package com.chad.community.dto;

import java.time.Instant;

public record CommentResponseDto(
        long commentId,
        String content,
        Instant createdAt,
        UserResponseDto writer
) {
}
