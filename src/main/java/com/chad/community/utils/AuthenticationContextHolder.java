package com.chad.community.utils;

public class AuthenticationContextHolder {
    private static final ThreadLocal<Integer> context = new ThreadLocal<Integer>();

    private AuthenticationContextHolder() {

    }

    public static Integer getContext() {
        return context.get();
    }

    public static void setContext(int userId) {
        context.set(userId);
    }

    public static void clear() {
        context.remove();
    }
}
