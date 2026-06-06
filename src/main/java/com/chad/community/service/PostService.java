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
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        return PostMapper.mapPostToPostResponse(post);
    }

    @Transactional
    public PostResponseDto getPostResponseById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        post.increaseViewCount();

        return PostMapper.mapPostToPostResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList(Long cursor,  int limit) {
        List<Post> postList;

        if (cursor == null) {
            postList = postRepository.findAllByOrderByIdDesc(Limit.of(limit));
        } else {
            postList = postRepository.findByIdLessThanOrderByIdDesc(cursor, Limit.of(limit));
        }

        return postList.stream()
                .map(PostMapper::mapPostToPostResponse)
                .toList();
    }

    @Transactional
    public PostResponseDto updatePostByIdWithAuth(AuthenticationInfo authenticationInfo,
                                                  long postId,
                                                  PostRequestDto postRequestDto
    ) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (post.getWriter().getId() != authenticationInfo.userId()) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        post.updatePost(postRequestDto.title(), postRequestDto.content(), postRequestDto.image());

        return PostMapper.mapPostToPostResponse(post);
    }

    @Transactional
    public void deletePostByIdWithAuth(AuthenticationInfo authenticationInfo, long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (post.getWriter().getId() != authenticationInfo.userId()) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        postRepository.deleteById(postId);
    }
}
