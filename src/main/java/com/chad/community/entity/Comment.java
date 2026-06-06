package com.chad.community.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT") @Setter
    private String content;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = true)
    private User writer;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Comment(String content, User writer, Post post) {
        this.content = content;
        this.writer = writer;
        this.post = post;
    }
}
