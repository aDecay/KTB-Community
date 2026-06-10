package com.chad.community.utils;

public record ApiResponse<T> (
    boolean success,
    T data,
    String message,
    Integer code
) {

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message, null);
    }


    public static <T> ApiResponse<T> fail(String message, int code) {
        return new ApiResponse<>(false, null, message, code);
    }
}
