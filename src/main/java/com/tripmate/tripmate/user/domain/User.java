package com.tripmate.tripmate.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private AuthType authType;
    private String nickname;
    private int age;
    private Gender gender;
    private Mbti mbti;
    private String role;


    @Builder
    private User(String username, String email, String password, AuthType authType, String nickname, int age, Gender gender, Mbti mbti, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authType = authType;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.mbti = mbti;
        this.role = role;
    }
}
