package com.depot.shopping.domain.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    // 환경변수 등록 필수 : 추후 application.yml 디폴트값 제외 예정
    @Value("${jwt.accessKey}")
    private String accessSecretKey;
    @Value("${jwt.refreshKey}")
    private String refreshSecretKey;
    private final long accessTokenValidity = 1000 * 60 * 5; // 5분
    private final long refreshTokenValidity = 1000 * 60 * 60; // 1시간
    private Key accessKey;
    private Key refreshKey;

    @PostConstruct
    protected void init() {
        if (accessSecretKey == null || accessSecretKey.isEmpty() || refreshSecretKey == null || refreshSecretKey.isEmpty()) {
            throw new IllegalStateException("❌ JWT_SECRET 환경 변수가 설정되지 않았습니다!");
        }

        byte[] decodedAccessKey = Base64.getDecoder().decode(accessSecretKey);
        byte[] decodedRefreshKey = Base64.getDecoder().decode(refreshSecretKey);

        this.accessKey = Keys.hmacShaKeyFor(decodedAccessKey);
        this.refreshKey = Keys.hmacShaKeyFor(decodedRefreshKey);
    }

    /**
     * Access Token 생성
     */
    public String createAccessToken(Long seqId) {
        return createToken(seqId, accessTokenValidity, accessKey);
    }

    /**
     * Refresh Token 생성
     */
    public String createRefreshToken(Long seqId) {
        return createToken(seqId, refreshTokenValidity, refreshKey);
    }

    private String createToken(Long seqId, long validity, Key key) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(String.valueOf(seqId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validity))
                .signWith(key, SignatureAlgorithm.HS256)
                //.claim("userIp", ip);
                .compact();
    }

    /**
     * 토큰 검증
     */
    public boolean validateToken(String token, boolean isRefreshToken) {
        try {
            Key signingKey = isRefreshToken ? refreshKey : accessKey;
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // ✅ 만료 시간 검증
            if (claims.getExpiration().before(new Date())) {
                return false;
            }

//            // ✅ 발급자 검증
//            if (!"my-server".equals(claims.getIssuer())) {
//                return false;
//            }
//
//            // ✅ Audience 검증
//            if (!"my-app".equals(claims.getAudience())) {
//                return false;
//            }

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
    public Authentication getAuthentication(String token, boolean isRefreshToken) {
        Key key = isRefreshToken ? refreshKey : accessKey;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userId = claims.getSubject(); // ✅ userId 꺼냄

        User user = new User(userId, "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
    }
}

