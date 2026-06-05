package com.chad.community.mapper;

import com.chad.community.dto.PostRequestDto;
import com.chad.community.dto.PostResponseDto;
import com.chad.community.entity.Post;
import com.chad.community.entity.User;

public class PostMapper {

    public static Post mapWriterAndPostRequestToPost(User writer, PostRequestDto postRequestDto) {
        return Post.builder()
                .writer(writer)
                .title(postRequestDto.title())
                .content(postRequestDto.content())
                .image(
                        postRequestDto.image() == null || postRequestDto.image().isBlank()
                        ? null
                        : postRequestDto.image()
                )
                .build();
    }

    public static PostResponseDto mapPostToPostResponseDto(Post post) {
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getViewCount(), UserMapper.mapUserToUserResponse(post.getWriter()));
    }
}
