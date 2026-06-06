package com.chad.community.mapper;

import com.chad.community.dto.CommentRequestDto;
import com.chad.community.dto.CommentResponseDto;
import com.chad.community.entity.Comment;
import com.chad.community.entity.Post;
import com.chad.community.entity.User;

public class CommentMapper {

    public static Comment mapCommentRequestToComment(User writer, Post post, CommentRequestDto commentRequestDto) {
        return Comment.builder()
                .writer(writer)
                .post(post)
                .content(commentRequestDto.content())
                .build();
    }

    public static CommentResponseDto mapCommentToCommentResponse(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getCreatedAt(), UserMapper.mapUserToUserResponse(comment.getWriter()));
    }
}
