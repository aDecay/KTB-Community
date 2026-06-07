package com.chad.community.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 26) @Setter
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT") @Setter
    private String content;
    @Column(nullable = true, length = 255) @Setter
    private String image;
    @Column(nullable = false)
    private int viewCount;                          // int는 자동 0 초기화
    @Column(nullable = false)
    private int commentCount;                       // int는 자동 0 초기화
    @Column(nullable = false)
    private int likeCount;                          // int는 자동 0 초기화
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = true)
    private User writer;

    @Builder
    public Post(User writer, String image, String content, String title) {
        this.writer = writer;
        this.image = image;
        this.content = content;
        this.title = title;
    }

    public void increaseViewCount() {
        viewCount++;
    }

    public void increaseCommentCount() {
        commentCount++;
    }

    public void decreaseCommentCount() {
        commentCount--;
    }

    public void increaseLikeCount() {
        likeCount++;
    }

    public void decreaseLikeCount() {
        likeCount--;
    }

    public void updatePost(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
