package com.chad.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDto (
        @NotBlank(message = "제목을 필수로 입력해야 합니다.")
        @Size(max = 26, message = "제목은 26자까지 입력 가능합니다.")
        String title,
        @NotBlank(message = "본문을 필수로 입력해야 합니다.")
        @Size(max = 15000, message = "본문은 15,000자까지 입력 가능합니다.")
        String content,
        @Size(max = 255, message = "이미지 경로는 255자까지 가능합니다.")
        String image
) {}