package com.chad.community.utils;

import java.util.Optional;

public class AuthenticationContextHolder {
    private static final ThreadLocal<Integer> context = new ThreadLocal<>();

    private AuthenticationContextHolder() {

    }

    public static Optional<Integer> getUserId() {
        return Optional.ofNullable(context.get());
    }

    public static void setUserId(int userId) {
        context.set(userId);
    }

    public static void clearUserId() {
        context.remove();
    }
}
