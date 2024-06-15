package com.tripmate.tripmate.common;

import lombok.Getter;

@Getter
public enum ResultCode {

    // 정상 처리
    OK("T000", "요청 정상 처리"),

    // 서버 내부 에러 (5xx 에러)
    INTERNAL_SERVER_ERROR("T100", "서버 에러 발생"),

    // F2xx: JSon 값 예외
    NOT_VALIDATION("P200", "json 값이 올바르지 않습니다."),

    // P3xx: 인증, 권한에 대한 예외
    DUPLICATE_EMAIL("P300","중복된 이메일 입니다."),
    UNAUTHORIZED_EMAIL("P301","인증되지 않은 이메일 입니다."),
    DUPLICATE_USERNAME("P302","중복된 username 입니다."),
    NOT_VALID_ACCESS_TOKEN("P303", "유효하지 않은 access token 입니다."),
    NOT_VALID_REFRESH_TOKEN("P304", "유효하지 않은 refresh token 입니다."),
    MAIL_SEND_FAIL("P305", "메일 전송에 실패했습니다."),




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
