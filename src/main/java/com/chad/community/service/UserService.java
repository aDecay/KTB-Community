package com.chad.community.service;

import com.chad.community.dto.UserExistenceResponseDto;
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

    public UserExistenceResponseDto checkExistence(String email, String nickname) {
        if (email == null && nickname == null) {
            throw new CustomException(ErrorCode.USER_INVALID_EXISTENCE_CHECK);
        }

        boolean emailExists = email == null || userRepository.userEmailExists(email);
        boolean nicknameExists = nickname == null || userRepository.userNicknameExists(nickname);

        return UserMapper.mapBooleanToUserDuplicationResponse(emailExists && nicknameExists);
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    public UserResponseDto getMyUser(int userId) {
        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return UserMapper.mapUserToUserResponse(user);
    }

    public void deleteMyUser(int userId) {
        userRepository.deleteUserById(userId);
    }
}
