package com.chad.community.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthRequestDto (
        @Size(max = 320, message = "이메일은 320자까지 가능합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+$",
                message = "올바른 이메일 형식이어야 합니다.")
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{8,20}$",
                message = "비밀번호는 8자 이상, 20자 이하이며, 대문자, 소문자, 특수문자를 각각 최소 1개 포함해야 합니다.")
        String password
) {}