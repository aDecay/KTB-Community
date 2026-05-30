package com.chad.community.service;

import com.chad.community.dto.UserResponseDto;
import com.chad.community.dto.UserRequestDto;
import com.chad.community.entity.User;
import com.chad.community.mapper.UserMapper;
import com.chad.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto userRequest) {
        User user = userRepository.saveUser(UserMapper.mapUserRequestToUser(userRequest));

        return UserMapper.mapUserToUserResponse(user);
    }
}
