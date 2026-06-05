package com.chad.community.entity;

import jakarta.persistence.*;
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
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Builder
    public Post(User user, String image, String content, String title) {
        this.user = user;
        this.image = image;
        this.content = content;
        this.title = title;
    }

    public void increaseViewCount() {
        viewCount++;
    }
}
