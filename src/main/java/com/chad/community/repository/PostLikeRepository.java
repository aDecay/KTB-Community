package com.chad.community.repository;

import com.chad.community.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserIdAndPostId(long userId, long postId);

    void deleteByUserIdAndPostId(long userId, long postId);
}
