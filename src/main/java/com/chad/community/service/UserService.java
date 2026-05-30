package com.chad.community.service;

import com.chad.community.dto.UserResponseDto;
import com.chad.community.dto.UserRequestDto;
import com.chad.community.entity.User;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.mapper.UserMapper;
import com.chad.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto userRequest) {
        // Email 중복 검사
        if (userRepository.userEmailExists(userRequest.email())) {
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);
        }

        // Nickname 중복 검사
        if (userRepository.userNicknameExists(userRequest.nickname())) {
            throw new CustomException(ErrorCode.USER_NICKNAME_DUPLICATED);
        }

        // User 저장
        User user = userRepository.saveUser(UserMapper.mapUserRequestToUser(userRequest));

        return UserMapper.mapUserToUserResponse(user);
    }
}
