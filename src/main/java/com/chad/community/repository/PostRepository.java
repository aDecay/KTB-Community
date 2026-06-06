package com.chad.community.repository;

import com.chad.community.entity.Post;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"writer"})
    Optional<Post> findById(long postId);

    @EntityGraph(attributePaths = {"writer"})
    List<Post> findAllByOrderByIdDesc(Limit limit);

    @EntityGraph(attributePaths = {"writer"})
    List<Post> findByIdLessThanOrderByIdDesc(long id, Limit limit);
}
