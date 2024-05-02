package com.tripmate.tripmate.auth.controller;


import com.tripmate.tripmate.auth.domain.RefreshToken;
import com.tripmate.tripmate.auth.dto.SignUpDto;
import com.tripmate.tripmate.auth.dto.request.CertificateEmailRequestDto;
import com.tripmate.tripmate.auth.dto.request.CertifyEmailRequestDto;
import com.tripmate.tripmate.auth.dto.request.SignUpRequestDto;
import com.tripmate.tripmate.auth.jwt.JWTUtil;
import com.tripmate.tripmate.auth.repository.RefreshTokenRepository;
import com.tripmate.tripmate.auth.service.AuthService;
import com.tripmate.tripmate.common.ResponseForm;
import com.tripmate.tripmate.common.exception.NotValidRefreshTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.tripmate.tripmate.auth.jwt.JwtProperties.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenRepository refreshRepository;
    private final JWTUtil jwtUtil;

    @PostMapping("/certification/generate")
    public ResponseForm<String> createCertificate(@RequestBody CertificateEmailRequestDto request) {
        authService.createCertification(request.getEmail());
        return new ResponseForm<>();
    }

    @PostMapping("/certification/certify")
    public ResponseForm certify(@RequestBody CertifyEmailRequestDto request) {
        authService.certifyPhoneNum(request.getEmail(), request.getCertificationNum());
        return new ResponseForm();
    }

    @PostMapping("/sign-up")
    public ResponseForm signUp(@RequestBody SignUpRequestDto request) {
        SignUpDto signUpDto = requestToDto(request);
        authService.signUp(signUpDto, request.getCertificationNum());
        return new ResponseForm();
    }

    @PostMapping("/reissue")
    public ResponseForm reissue(HttpServletRequest request, HttpServletResponse response) throws BadRequestException {

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN_KEY_STRING)) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            //response status code
            throw new NotValidRefreshTokenException();
        }

        //expired check
        if(jwtUtil.isExpired(refresh)){
            throw new NotValidRefreshTokenException();
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals(REFRESH_TOKEN_KEY_STRING)) {
            //response status code
            throw new NotValidRefreshTokenException();
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            //response body
            throw new NotValidRefreshTokenException();
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt(ACCESS_TOKEN_KEY_STRING, username, role, ACCESS_TOKEN_EXPIRED_MS);
        String newRefresh = jwtUtil.createJwt(REFRESH_TOKEN_KEY_STRING, username, role, REFRESH_TOKEN_EXPIRED_MS);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        //response
        response.setHeader(ACCESS_TOKEN_KEY_STRING, newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseForm<>();
    }

    private static SignUpDto requestToDto(SignUpRequestDto requestDto) {
        return SignUpDto.builder()
                .username(requestDto.getUsername())
                .age(requestDto.getAge())
                .gender(requestDto.getGender())
                .mbti(requestDto.getMbti())
                .nickname(requestDto.getNickname())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword()).build();
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshEntity = RefreshToken.builder()
                .username(username)
                .refresh(refresh)
                .expiration(date.toString())
                .build();
        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
