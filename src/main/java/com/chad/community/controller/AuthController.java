package com.chad.community.controller;

import com.chad.community.dto.AuthRequestDto;
import com.chad.community.dto.AuthResponseDto;
import com.chad.community.service.AuthService;
import com.chad.community.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/auths")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        AuthResponseDto token = authService.createToken(authRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(token, "logged in successfully"));
    }
}
