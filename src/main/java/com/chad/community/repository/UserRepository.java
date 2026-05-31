package com.chad.community.repository;

import com.chad.community.dto.UserResponseDto;
import com.chad.community.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class UserRepository {
    private final ArrayList<User> userList = new ArrayList<>();
    private int nextId = 0;

    public User saveUser(User user) {
        user.setId(nextId++);
        userList.add(user);
        return user;
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userList.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst();
    }

    public boolean userEmailExists(String email) {
        return userList.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    public boolean userNicknameExists(String nickname) {
        return userList.stream().anyMatch(user -> user.getNickname().equals(nickname));
    }

    public Optional<User> findUserById(int id) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    public void deleteUserById(int userId) {
        userList.removeIf(user -> userId == user.getId());
    }
}
