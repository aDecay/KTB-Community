package com.chad.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequestDto(
        @NotBlank(message = "댓글 내용을 필수로 입력해야 합니다.")
        @Size(max = 255, message = "댓글 내용은 255자까지 입력 가능합니다.")
        String content
) {
}
