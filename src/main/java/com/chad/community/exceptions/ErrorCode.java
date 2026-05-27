package com.chad.community.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, -1001, "unauthorized");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
