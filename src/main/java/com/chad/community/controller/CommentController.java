package com.chad.community.controller;

import com.chad.community.annotation.AuthenticationParameter;
import com.chad.community.dto.AuthenticationInfo;
import com.chad.community.dto.CommentRequestDto;
import com.chad.community.dto.CommentResponseDto;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.service.CommentService;
import com.chad.community.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/v1/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @PathVariable long postId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        CommentResponseDto comment = commentService.createComment(authenticationInfo, postId, commentRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(comment, "comment created successfully"));
    }

    @GetMapping("/v1/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getCommentList(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @PathVariable long postId
    ) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        List<CommentResponseDto> comment = commentService.getCommentList(postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(comment, "comment list found successfully"));
    }

    @PutMapping("/v1/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @PathVariable long commentId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        CommentResponseDto comment = commentService.updateComment(authenticationInfo, commentId, commentRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(comment, "comment updated successfully"));
    }

    @DeleteMapping("/v1/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationParameter AuthenticationInfo authenticationInfo,
            @PathVariable long commentId
    ) {
        if (authenticationInfo == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        commentService.deletePost(authenticationInfo, commentId);

        return ResponseEntity.noContent().build();
    }
}
