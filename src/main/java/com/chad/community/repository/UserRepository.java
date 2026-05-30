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
}
