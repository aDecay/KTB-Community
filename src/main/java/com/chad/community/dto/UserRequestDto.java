package com.chad.community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
    @Size(max = 320, message = "이메일은 320자까지 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+$",
            message = "올바른 이메일 형식이어야 합니다.")
    String email,

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{8,20}$",
            message = "비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 특수문자를 각각 최소 1개 포함해야 합니다.")
    String password,

    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,10}$",
            message = "닉네임은 띄어쓰기 없이 10자까지 작성 가능합니다.")
    String nickname,

    @NotBlank(message = "프로필 이미지를 필수로 등록해야 합니다.")
    @Size(max = 255, message = "프로필 이미지 경로는 255자까지 가능합니다.")
    String profileImageUrl
) {}
