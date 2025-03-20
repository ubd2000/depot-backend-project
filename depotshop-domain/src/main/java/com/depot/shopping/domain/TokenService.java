package com.depot.shopping.domain;

import com.depot.shopping.domain.config.JwtProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collections;

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

    public Authentication getAuthentication(String username) {
        User user = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

}
