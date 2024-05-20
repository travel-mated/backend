package com.tripmate.tripmate.auth.oauth;

import com.tripmate.tripmate.auth.PrincipalDetails;
import com.tripmate.tripmate.auth.domain.RefreshToken;
import com.tripmate.tripmate.auth.jwt.JWTUtil;
import com.tripmate.tripmate.auth.repository.RefreshTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import static com.tripmate.tripmate.auth.jwt.JwtProperties.ACCESS_TOKEN_EXPIRED_MS;
import static com.tripmate.tripmate.auth.jwt.JwtProperties.REFRESH_TOKEN_EXPIRED_MS;

@Component
@RequiredArgsConstructor
public class OauthCustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        //OAuth2User
        PrincipalDetails customUserDetails = (PrincipalDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String access = jwtUtil.createJwt("access", username, role, ACCESS_TOKEN_EXPIRED_MS);
        String refresh = jwtUtil.createJwt("refresh", username, role, REFRESH_TOKEN_EXPIRED_MS);

        addRefreshEntity(username, refresh, REFRESH_TOKEN_EXPIRED_MS);

        //응답 설정
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
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