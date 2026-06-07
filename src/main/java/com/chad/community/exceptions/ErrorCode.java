package com.chad.community.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, -1001, "unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, -1002, "no permission"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -1003, "server error"),
    AUTH_FAILED(HttpStatus.BAD_REQUEST, -2001, "auth failed"),
    AUTH_EXPIRED(HttpStatus.UNAUTHORIZED, -2002, "auth expired"),
    AUTH_INVALID(HttpStatus.UNAUTHORIZED, -2003, "invalid auth"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, -3001, "user not found"),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, -3002, "user email duplicated"),
    USER_NICKNAME_DUPLICATED(HttpStatus.CONFLICT, -3003, "user nickname duplicated"),
    USER_INVALID_EXISTENCE_CHECK(HttpStatus.BAD_REQUEST, -3004, "email or nickname must be specified"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, -4001, "post not found"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, -5001, "comment not found"),
    POSTLIKE_NOT_FOUND(HttpStatus.NOT_FOUND, -6001, "post like not found"),
    POSTLIKE_DUPLICATED(HttpStatus.CONFLICT, -6002, "post like duplicated"),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, -7001, "image not found"),
    IMAGE_INVALID_TYPE(HttpStatus.BAD_REQUEST, -7002, "invalid image type"),
    INVALID_IMAGE_FORMAT(HttpStatus.BAD_REQUEST, -7003, "invalid image format");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
