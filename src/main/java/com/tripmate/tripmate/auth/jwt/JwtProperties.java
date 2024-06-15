package com.tripmate.tripmate.auth.jwt;

public interface JwtProperties {
    String SECRET = "cos"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    Long ACCESS_TOKEN_EXPIRED_MS = 600000L; // 10분
    Long REFRESH_TOKEN_EXPIRED_MS = 86400000L; //1일
    String TOKEN_PREFIX = "Bearer ";
    String TOKEN_PREFIX_UTF8 = "Bearer+";
    String HEADER_STRING = "Authorization";
    String ACCESS_TOKEN_KEY_STRING = "access";
    String REFRESH_TOKEN_KEY_STRING = "refresh";
}
