package com.chad.community.mapper;

import com.chad.community.dto.PostLikeResponseDto;
import com.chad.community.entity.PostLike;
import com.chad.community.entity.Post;
import com.chad.community.entity.User;

public class PostLikeMapper {
    public static PostLike mapUserAndPostToPostLike(User user, Post post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .build();
    }


    public static PostLikeResponseDto mapCountAndLikedToPostLikeResponse(int likeCount, boolean liked) {
        return new PostLikeResponseDto(likeCount, liked);
    }
}
