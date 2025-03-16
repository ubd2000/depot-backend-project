//package com.depot.shopping.config;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtProvider {
//    private static final String SECRET_KEY = "MySecretKey"; // 🔹 실제 환경에서는 환경 변수로 관리해야 함
//    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간 (밀리초)
//
//    public String createToken(Authentication authentication) {
//        User user = (User) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject(user.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//    public String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//        User user = new User(claims.getSubject(), "", new ArrayList<>());
//        return new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
//    }
//
//}
//
