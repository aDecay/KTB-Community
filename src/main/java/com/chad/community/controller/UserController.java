package com.chad.community.controller;

import com.chad.community.dto.UserResponseDto;
import com.chad.community.dto.UserRequestDto;
import com.chad.community.service.UserService;
import com.chad.community.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
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

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyUser() {
        return null;
    }
}
