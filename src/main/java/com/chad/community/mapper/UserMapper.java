package com.chad.community.mapper;

import com.chad.community.dto.UserExistenceResponseDto;
import com.chad.community.dto.UserResponseDto;
import com.chad.community.dto.UserRequestDto;
import com.chad.community.entity.User;

public class UserMapper {
    public static User mapUserRequestToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.email());
        user.setPassword(userRequestDto.password());
        user.setNickname(userRequestDto.nickname());
        user.setProfileImage(userRequestDto.profileImage());

        return user;
    }

    public static UserResponseDto mapUserToUserResponse(User user) {
        return new UserResponseDto(user.getId(), user.getNickname(), user.getEmail());
    }

    public static UserExistenceResponseDto mapBooleanToUserDuplicationResponse(boolean exists) {
        return new UserExistenceResponseDto(exists);
    }
}