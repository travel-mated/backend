package com.tripmate.tripmate.common;

import lombok.Getter;

@Getter
public enum ResultCode {

    // 정상 처리
    OK("T000", "요청 정상 처리"),

    // 서버 내부 에러 (5xx 에러)
    INTERNAL_SERVER_ERROR("T100", "서버 에러 발생"),

    // T2xx: JSon 값 예외
    NOT_VALIDATION("T200", "json 값이 올바르지 않습니다."),

    // T3xx: 인증, 권한에 대한 예외


    // T4xx: 유저 예외
    USER_NOT_FOUND("T400", "존재하지 않는 유저입니다."),

    //T5xx: 댓글 예외
    POST_COMMENT_NOT_FOUND("T500", "존재하지 않은 게시글 댓글입니다."),
    POST_COMMENT_NOT_WRITER("T501", "게시글 댓글 작성자가 아닙니다."),

    //T6xx: 게시글 예외
    POST_NOT_FOUND("T600", "존재하지 않은 게시글입니다.");


    private final String code;
    private final String message;


    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
