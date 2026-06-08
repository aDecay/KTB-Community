package com.chad.community.utils;

public interface PasswordHelper {
    String hashPassword(String password);

    boolean checkPassword(String plain, String hashed);
}
