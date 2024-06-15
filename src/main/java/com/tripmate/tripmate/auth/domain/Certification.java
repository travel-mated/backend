package com.tripmate.tripmate.auth.domain;

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
public class Certification {
    @Id
    @GeneratedValue
    Long id;
    String email;
    String certificationNum;
    Boolean isCheck;


    public void check(){
        isCheck = true;
    }

    @Builder
    private Certification(Long id, String email, String certificationNum, boolean isCheck) {
        this.id = id;
        this.email = email;
        this.certificationNum = certificationNum;
        this.isCheck = isCheck;
    }
}
