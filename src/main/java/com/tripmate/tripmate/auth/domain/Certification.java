package com.tripmate.tripmate.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification {
    @Id
    @GeneratedValue
    Long id;
    String phoneNum;
    String certificationNum;
    Boolean isCheck;


    public void check(){
        isCheck = true;
    }

    @Builder
    private Certification(Long id, String phoneNum, String certificationNum, boolean isCheck) {
        this.id = id;
        this.phoneNum = phoneNum;
        this.certificationNum = certificationNum;
        this.isCheck = isCheck;
    }
}
