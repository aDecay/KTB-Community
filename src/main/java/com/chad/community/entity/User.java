package com.chad.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 320)
    private String email;
    @Column(nullable = false, length = 128) @Setter
    private String password;
    @Column(nullable = false, length = 10) @Setter
    private String nickname;
    @Column(nullable = false, length = 255) @Setter
    private String profileImageUrl;

    @Builder
    public User(String email, String password, String nickname, String profileImageUrl) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
