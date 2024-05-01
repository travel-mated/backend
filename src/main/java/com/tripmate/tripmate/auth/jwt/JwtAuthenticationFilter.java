package com.tripmate.tripmate.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmate.tripmate.auth.PrincipalDetails;
import com.tripmate.tripmate.auth.domain.RefreshToken;
import com.tripmate.tripmate.auth.dto.request.LoginRequestDto;
import com.tripmate.tripmate.auth.repository.RefreshTokenRepository;
import com.tripmate.tripmate.user.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

import static com.tripmate.tripmate.auth.jwt.JwtProperties.ACCESS_TOKEN_EXPIRED_MS;
import static com.tripmate.tripmate.auth.jwt.JwtProperties.REFRESH_TOKEN_EXPIRED_MS;

/**
 * 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
 * login 요청해서 username, password 전송을 하면 (post)
 * UsernamePasswordAuthenticationFilter 동작
 */

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final boolean postOnly = true;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter 로그인 : 진입");
        //로그인 요청 시 들어온 데이터를 객체로 변환
        ObjectMapper om = new ObjectMapper();
        LoginRequestDto userLoginDto = null;
        try {
            userLoginDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read JSON from request body", e);
        }
        String loginId = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginId, password);

        System.out.println("JwtAuthenticationFilter : 토큰생성완료");
        System.out.println(authenticationToken);

        // PrincipalDetailsService의 loadUserByUsername() 실행
        // DB에 있는 username 과 password 가 일치한다.
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principalDetails = " + principalDetails.getUser());

        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        username = (username != null) ? username.trim() : "";
        password = obtainPassword(request);
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return authentication;
        /**
         *  1. username, password 를 받은 후
         *  2. 정상인지 로그인 시도를 해본다  this.getAuthenticationManager().authenticate(authRequest)
         *     PrincipalDetailsService 호출 loadUserByUsername() 함수 실행
         *  3. PrincipalDetails 를 세션에 담고
         *  4. JWT token 을 만들어서 응답해주면 됨
         */
    }


    /**
     * 이 함수( attemptAuthentication ) 실행 후 인증이 정상적으로 이루어 지면 successfulAuthentication 함수가 실행 됨
     * JWT 토큰을 만들어 response 에 JWT 토큰을 담아 사용자에게 보냄
     */


    /**
     * RSA 방식은 아니고 Hash 암호방식
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행 됨 - 인증 완료");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        User user = principalDetails.getUser();


        //또한 JwtToken 을 쿠키에 넣어 보내준다.
        String access = jwtUtil.createJwt("access", user.getUsername(), user.getRole(), ACCESS_TOKEN_EXPIRED_MS);
        String refresh = jwtUtil.createJwt("refresh", user.getUsername(), user.getRole(), REFRESH_TOKEN_EXPIRED_MS);

        //Refresh 토큰 저장
        addRefreshEntity(user.getUsername(), refresh, REFRESH_TOKEN_EXPIRED_MS);

        //응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
        /**
         *  임시 redirect 주소
         */
        response.sendRedirect("http://localhost:3000/");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(Math.toIntExact(REFRESH_TOKEN_EXPIRED_MS));
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshEntity = RefreshToken.builder()
                .refresh(refresh)
                .username(username)
                .expiration(date.toString())
                .build();

        refreshTokenRepository.save(refreshEntity);
    }
}
