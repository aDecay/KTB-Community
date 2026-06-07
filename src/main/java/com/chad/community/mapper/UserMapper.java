package com.chad.community.mapper;

import com.chad.community.dto.UserExistenceResponseDto;
import com.chad.community.dto.UserResponseDto;
import com.chad.community.dto.UserRequestDto;
import com.chad.community.entity.User;

public class UserMapper {
    public static User mapUserRequestToUser(UserRequestDto userRequestDto) {
        return User.builder()
                .email(userRequestDto.email())
                .password(userRequestDto.password())
                .nickname(userRequestDto.nickname())
                .profileImageUrl(userRequestDto.profileImageUrl())
                .build();
    }

    public static UserResponseDto mapUserToUserResponse(User user) {
        return new UserResponseDto(user.getId(), user.getNickname(), user.getProfileImageUrl());
    }

    public static UserExistenceResponseDto mapBooleanToUserDuplicationResponse(boolean exists) {
        return new UserExistenceResponseDto(exists);
    }
}