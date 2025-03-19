package com.depot.shopping.domain.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.Jwts;

@Component
public class JwtProvider {
    // 환경변수 등록 필수 : 추후 application.yml 디폴트값 제외 예정
    @Value("${jwt.secret}")
    private String secretKey;
    private final long accessTokenValidity = 1000 * 60 * 5; // 5분
    private final long refreshTokenValidity = 1000 * 60 * 60; // 1시간
    private Key key;

    @PostConstruct
    protected void init() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("❌ JWT_SECRET 환경 변수가 설정되지 않았습니다!");
        }

        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * Access Token 생성
     */
    public String createAccessToken(String username) {
        return createToken(username, accessTokenValidity);
    }

    /**
     * Refresh Token 생성
     */
    public String createRefreshToken(String username) {
        return createToken(username, refreshTokenValidity);
    }

    private String createToken(String username, long validity) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validity))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 토큰에서 사용자 정보 추출
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        User user = new User(claims.getSubject(), "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
    }
}

