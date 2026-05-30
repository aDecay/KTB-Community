package com.chad.community.repository;

import com.chad.community.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepository {
    private final ArrayList<User> userList = new ArrayList<>();
    private int nextId = 0;

    public User saveUser(User user) {
        user.setId(nextId++);
        userList.add(user);
        return user;
    }

    public boolean userEmailExists(String email) {
        for (User user : userList) {
            if (email.equals(user.getEmail())) {
                return true;
            }
        }

        return false;
    }

    public boolean userNicknameExists(String nickname) {
        for (User user : userList) {
            if (nickname.equals(user.getNickname())) {
                return true;
            }
        }

        return false;
    }
}
