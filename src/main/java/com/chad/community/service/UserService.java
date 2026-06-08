package com.chad.community.service;

import com.chad.community.dto.UserExistenceResponseDto;
import com.chad.community.dto.UserResponseDto;
import com.chad.community.dto.UserRequestDto;
import com.chad.community.dto.UserUpdateRequestDto;
import com.chad.community.entity.User;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.mapper.UserMapper;
import com.chad.community.repository.UserRepository;
import com.chad.community.utils.PasswordHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordHelper passwordHelper;

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequest) {
        // Email 중복 검사
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);
        }

        // Nickname 중복 검사
        if (userRepository.existsByNickname(userRequest.nickname())) {
            throw new CustomException(ErrorCode.USER_NICKNAME_DUPLICATED);
        }

        String hashedPassword = passwordHelper.hashPassword(userRequest.password());

        // User 저장
        User user = userRepository.save(UserMapper.mapUserRequestToUser(userRequest, hashedPassword));

        return UserMapper.mapUserToUserResponse(user);
    }

    @Transactional(readOnly = true)
    public UserExistenceResponseDto checkExistence(String email, String nickname) {
        if (email == null && nickname == null) {
            throw new CustomException(ErrorCode.USER_INVALID_EXISTENCE_CHECK);
        }

        boolean emailExists = email == null || userRepository.existsByEmail(email);
        boolean nicknameExists = nickname == null || userRepository.existsByNickname(nickname);

        return UserMapper.mapBooleanToUserDuplicationResponse(emailExists && nicknameExists);
    }

    @Transactional(readOnly = true)
    public User findUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user != null && passwordHelper.checkPassword(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public UserResponseDto getMyUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserMapper.mapUserToUserResponse(user);
    }

    @Transactional
    public UserResponseDto updateMyUser(int userId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (userUpdateRequestDto.password().isPresent()) {
            String password = passwordHelper.hashPassword(userUpdateRequestDto.password().get());
            user.setPassword(password);
        }

        if (userUpdateRequestDto.nickname().isPresent()) {
            user.setNickname(userUpdateRequestDto.nickname().get());
        }

        if (userUpdateRequestDto.profileImage().isPresent()) {
            user.setProfileImageUrl(userUpdateRequestDto.profileImage().get());
        }

        return UserMapper.mapUserToUserResponse(user);
    }

    @Transactional
    public void deleteMyUser(int userId) {
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public User findUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
