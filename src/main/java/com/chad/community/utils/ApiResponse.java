package com.chad.community.utils;

public record ApiResponse<T> (
    boolean success,
    T data,
    String message
) {

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message);
    }


    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, null, message);
    }
}
