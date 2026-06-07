package com.chad.community.service;

import com.chad.community.dto.AuthenticationInfo;
import com.chad.community.dto.PostLikeResponseDto;
import com.chad.community.entity.PostLike;
import com.chad.community.entity.Post;
import com.chad.community.entity.User;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.mapper.PostLikeMapper;
import com.chad.community.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    @Transactional
    public PostLikeResponseDto addPostLike(AuthenticationInfo authenticationInfo, long postId) {
        User user = userService.findUserById(authenticationInfo.userId());
        Post post = postService.findPostById(postId);

        if (likeRepository.existsByUserIdAndPostId(user.getId(), post.getId())) {
            throw new CustomException(ErrorCode.POSTLIKE_DUPLICATED);
        }

        PostLike postLike = PostLikeMapper.mapUserAndPostToLike(user, post);
        likeRepository.save(postLike);

        post.increaseLikeCount();

        return PostLikeMapper.mapCountAndLikedToLikeResponse(post.getLikeCount(), true);
    }
}
