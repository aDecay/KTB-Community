package com.chad.community.utils;

import java.util.Optional;

public class AuthenticationContextHolder {
    private static final ThreadLocal<Integer> context = new ThreadLocal<Integer>();

    private AuthenticationContextHolder() {

    }

    public static Optional<Integer> getContext() {
        return Optional.ofNullable(context.get());
    }

    public static void setContext(int userId) {
        context.set(userId);
    }

    public static void clear() {
        context.remove();
    }
}
