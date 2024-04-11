package com.tripmate.tripmate.auth.dto.request;


import com.tripmate.tripmate.user.domain.Gender;
import com.tripmate.tripmate.user.domain.Mbti;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private String username;
    private String password;
    private String nickname;
    private String phoneNumber;
    private int age;
    private Gender gender;
    private Mbti mbti;
    String certificationNum;

    @Builder
    private SignUpRequestDto(String username, String password, String nickname, String phoneNumber, int age, Gender gender, Mbti mbti, String certificationNum) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.mbti = mbti;
        this.certificationNum = certificationNum;
    }
}
