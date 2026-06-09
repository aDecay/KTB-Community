package com.chad.community.controller;

import com.chad.community.annotation.AuthenticationParameter;
import com.chad.community.dto.*;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.service.UserService;
import com.chad.community.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody @Valid UserRequestDto userRequest) {
        UserResponseDto user = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(user, "user created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserExistenceResponseDto>> checkExistence(@RequestParam(required = false) String email, @RequestParam(required = false) String nickname) {
        UserExistenceResponseDto userDuplication = userService.checkExistence(email, nickname);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(userDuplication, "user existence checked successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> getMyUser(@AuthenticationParameter AuthenticationInfo authenticationInfo) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        UserResponseDto user = userService.getMyUser(authenticationInfo.userId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(user, "user found successfully"));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateMyUser(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        UserResponseDto user = userService.updateMyUser(authenticationInfo.userId(), userUpdateRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(user, "user updated successfully"));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyUser(@AuthenticationParameter AuthenticationInfo authenticationInfo) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        userService.deleteMyUser(authenticationInfo.userId());

        return ResponseEntity.noContent().build();
    }
}
