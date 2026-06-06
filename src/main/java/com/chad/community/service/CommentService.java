package com.chad.community.service;

import com.chad.community.dto.AuthenticationInfo;
import com.chad.community.dto.CommentRequestDto;
import com.chad.community.dto.CommentResponseDto;
import com.chad.community.entity.Comment;
import com.chad.community.entity.Post;
import com.chad.community.entity.User;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.mapper.CommentMapper;
import com.chad.community.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Transactional
    public CommentResponseDto createComment(
            AuthenticationInfo authenticationInfo,
            long postId,
            CommentRequestDto commentRequestDto
    ) {
        User user = userService.findUserById(authenticationInfo.userId());
        Post post = postService.findPostById(postId);
        Comment comment = commentRepository.save(CommentMapper.mapCommentRequestToComment(user, post, commentRequestDto));

        return CommentMapper.mapCommentToCommentResponse(comment);
    }

    @Transactional
    public void deletePost(AuthenticationInfo authenticationInfo, long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (comment.getWriter().getId() != authenticationInfo.userId()) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        commentRepository.deleteById(comment.getId());
    }
}
