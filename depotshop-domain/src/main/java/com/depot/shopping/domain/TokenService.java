//package com.depot.shopping.domain;
//
//import com.depot.shopping.domain.config.JwtProvider;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TokenService {
//    private final JwtProvider jwtProvider;
//
//    public TokenService(JwtProvider jwtProvider) {
//        this.jwtProvider = jwtProvider;
//    }
//
//    public String generateAccessToken(String username) {
//        return jwtProvider.createToken(username);
//    }
//
//    public boolean validateToken(String token) {
//        return jwtProvider.validateToken(token);
//    }
//
//    public Authentication getAuthentication(String username) {
//        User user = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
//    }
//
//}
