package com.depot.shopping.api.config;

import com.depot.shopping.domain.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false; // 인증되지 않은 사용자
        }

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals(role));
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenService tokenService) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> authorize
//                            .requestMatchers("/signin").permitAll()
                                .requestMatchers("/auth/refresh", "/auth/login", "/oauth/**", "/favicon.ico", "/api/signup").permitAll() // 해당 주소 접근은 허용
//                                .requestMatchers("/swagger-ui/**").permitAll() // 해당 주소 접근은 허용
//                                .requestMatchers("/api/user/**").permitAll() // 해당 주소 접근은 허용
//                                .requestMatchers("/api/check/**").permitAll() // 해당 주소 접근은 허용
//                                .requestMatchers("/").permitAll() // 해당 주소 접근은 허용
//                                .requestMatchers("/login").permitAll() // 해당 주소 접근은 허용
//                                .requestMatchers("/images/**", "/js/**", "/css/**").permitAll() // 해당 주소 접근은 허용
//                                .requestMatchers("/admin/**").hasRole("admin")    // 관리자쪽 맵핑은 관리자 권한
                                .anyRequest().authenticated()   // 그 외 요청 인증필요
                )

                .logout((logout) -> logout
                        .logoutSuccessUrl("/main")
                        .invalidateHttpSession(true))

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT Token 인증방식으로 세션은 필요없으므로 비활성화
                )

                .addFilterBefore(new JwtFilter(tokenService, List.of(
                        "/auth/refresh",  // refreshToken 으로 accessToken 발급 요청시 컨트롤러에서 처리
                        "/auth/login",   // 로그인 요청시, 컨트롤러에서 처리
                        "/oauth/",     // SNS 로그인 요청시 컨트롤러에서 처리
                        "/api/signup",  // 일반회원 회원가입
                        "/favicon.ico"  // 이미지 등 모든 컨텐츠 파일은 프론트에서 처리
                )), UsernamePasswordAuthenticationFilter.class); // 🔹 특정 URL 제외 설정

        // 사용자 인증처리 컴포넌트 서비스 등록
        //http.userDetailsService(service);

        return http.build();
    }
}
