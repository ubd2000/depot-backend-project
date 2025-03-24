package com.depot.shopping.domain;

import com.depot.shopping.domain.config.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final JwtProvider jwtProvider;

    public TokenService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * accessToken 생성
     */
    public String generateAccessToken(String userId) {
        return jwtProvider.createAccessToken(userId);
    }

    /**
     * refreshToken 생성
     */
    public String generateRefreshToken(String userId) {
        return jwtProvider.createRefreshToken(userId);
    }

    /**
     * 토큰 검증
     */
    public boolean validateToken(String token, boolean isRefreshToken) {
        return jwtProvider.validateToken(token, isRefreshToken);
    }

    /**
     * 토큰정보 추출
     */
    public String resolveToken(HttpServletRequest request) {
        return jwtProvider.resolveToken(request);
    }

    /**
     * 토큰 내 사용자 정보 추출
     */
    public Authentication getAuthentication(String token, boolean isRefreshToken) {
        return jwtProvider.getAuthentication(token, isRefreshToken);
    }

}
