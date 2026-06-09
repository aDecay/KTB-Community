package com.chad.community.controller;

import com.chad.community.annotation.AuthenticationParameter;
import com.chad.community.dto.AuthenticationInfo;
import com.chad.community.dto.PostLikeResponseDto;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.service.PostLikeService;
import com.chad.community.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/v1/posts/{postId}/likes")
    public ResponseEntity<ApiResponse<PostLikeResponseDto>> addPostLike(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @PathVariable long postId
    ) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        PostLikeResponseDto postLike = postLikeService.addPostLike(authenticationInfo, postId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(postLike, "post like added successfully"));
    }

    @GetMapping("/v1/posts/{postId}/likes")
    public ResponseEntity<ApiResponse<PostLikeResponseDto>> getPostLike(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @PathVariable long postId
    ) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        PostLikeResponseDto postLike = postLikeService.getPostLike(authenticationInfo, postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(postLike, "post like found successfully"));
    }

    @DeleteMapping("/v1/posts/{postId}/likes")
    public ResponseEntity<ApiResponse<PostLikeResponseDto>> deletePostLike(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @PathVariable long postId
    ) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        PostLikeResponseDto postLike = postLikeService.deletePostLike(authenticationInfo, postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(postLike, "post unliked successfully"));
    }
}
