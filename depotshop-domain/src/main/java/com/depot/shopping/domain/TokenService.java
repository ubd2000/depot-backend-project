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

    public String generateAccessToken(String username) {
        return jwtProvider.createAccessToken(username);
    }

    public String generateRefreshToken(String username) {
        return jwtProvider.createRefreshToken(username);
    }

    public boolean validateToken(String token) {
        return jwtProvider.validateToken(token);
    }

    public Authentication getAuthentication(String username) {
        User user = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

}
