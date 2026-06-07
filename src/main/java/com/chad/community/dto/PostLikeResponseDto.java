package com.chad.community.dto;

public record PostLikeResponseDto(
        int count,
        boolean liked
) {
}
