package com.chad.community.controller;

import com.chad.community.annotation.AuthenticationParameter;
import com.chad.community.dto.AuthenticationInfo;
import com.chad.community.dto.PostRequestDto;
import com.chad.community.dto.PostResponseDto;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.service.PostService;
import com.chad.community.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/posts")
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @RequestBody @Valid PostRequestDto postRequestDto) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        PostResponseDto post = postService.createPostWithAuth(authenticationInfo, postRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(post, "post created successfully"));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPost(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @PathVariable long postId
    ) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        PostResponseDto post = postService.getPostResponseById(postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(post, "post found successfully"));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @PathVariable long postId
    ) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        postService.deletePostByIdWithAuth(postId, authenticationInfo);

        return ResponseEntity.noContent().build();
    }
}
