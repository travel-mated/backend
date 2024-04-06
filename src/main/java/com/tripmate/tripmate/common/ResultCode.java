package com.tripmate.tripmate.common;

import lombok.Getter;

@Getter
public enum ResultCode {

    // 정상 처리
    OK("P000", "요청 정상 처리"),

    // 서버 내부 에러 (5xx 에러)
    INTERNAL_SERVER_ERROR("P100", "서버 에러 발생"),

    // F2xx: JSon 값 예외
    NOT_VALIDATION("P200", "json 값이 올바르지 않습니다.");

    // P3xx: 인증, 권한에 대한 예외


    // P4xx: 유저 예외

    private final String code;
    private final String message;


    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
