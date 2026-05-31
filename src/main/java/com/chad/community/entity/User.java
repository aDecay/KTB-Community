package com.chad.community.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private int id;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
}
