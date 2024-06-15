package com.tripmate.tripmate.auth.dto;

import com.tripmate.tripmate.user.domain.Gender;
import com.tripmate.tripmate.user.domain.Mbti;
import lombok.Builder;
import lombok.Getter;


@Getter
public class SignUpDto {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private int age;
    private Gender gender;
    private Mbti mbti;

    @Builder
    private SignUpDto(String username, String password, String nickname, String email, int age, Gender gender, Mbti mbti) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.mbti = mbti;
    }
}
