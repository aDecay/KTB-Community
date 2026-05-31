package com.chad.community.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, -1001, "unauthorized"),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, -3002, "user email duplicated"),
    USER_NICKNAME_DUPLICATED(HttpStatus.CONFLICT, -3003, "user nickname duplicated"),
    USER_INVALID_EXISTENCE_CHECK(HttpStatus.BAD_REQUEST, -3004, "email or nickname must be specified");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
