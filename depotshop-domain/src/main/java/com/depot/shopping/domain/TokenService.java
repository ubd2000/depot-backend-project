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

    public String generateAccessToken(String userId) {
        return jwtProvider.createAccessToken(userId);
    }

    public String generateRefreshToken(String userId) {
        return jwtProvider.createRefreshToken(userId);
    }

    public boolean validateToken(String token, boolean isRefreshToken) {
        return jwtProvider.validateToken(token, isRefreshToken);
    }

    public String resolveToken(HttpServletRequest request) {
        return jwtProvider.resolveToken(request);
    }

    public Authentication getAuthentication(String token, boolean isRefreshToken) {
        return jwtProvider.getAuthentication(token, isRefreshToken);
    }

}
