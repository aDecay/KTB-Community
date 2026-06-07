package com.chad.community.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequestDto(
        @NotNull(message = "파일을 필수로 업로드해야 합니다.")
        MultipartFile file
) {
}
