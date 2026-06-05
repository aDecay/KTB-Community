package com.chad.community.service;

import com.chad.community.dto.AuthenticationInfo;
import com.chad.community.dto.PostRequestDto;
import com.chad.community.dto.PostResponseDto;
import com.chad.community.entity.Post;
import com.chad.community.entity.User;
import com.chad.community.exceptions.CustomException;
import com.chad.community.exceptions.ErrorCode;
import com.chad.community.mapper.PostMapper;
import com.chad.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public PostResponseDto createPostWithAuth(
            AuthenticationInfo authenticationInfo,
            PostRequestDto postRequestDto) {
        User writer = userService.findUserById(authenticationInfo.userId());
        Post post = postRepository.save(PostMapper.mapWriterAndPostRequestToPost(writer, postRequestDto));

        return PostMapper.mapPostToPostResponseDto(post);
    }

    @Transactional
    public PostResponseDto getPostResponseById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        post.increaseViewCount();

        return PostMapper.mapPostToPostResponseDto(post);
    }

    @Transactional
    public void deletePostByIdWithAuth(long postId, AuthenticationInfo authenticationInfo) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (post.getWriter().getId() != authenticationInfo.userId()) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        postRepository.deleteById(postId);
    }
}
