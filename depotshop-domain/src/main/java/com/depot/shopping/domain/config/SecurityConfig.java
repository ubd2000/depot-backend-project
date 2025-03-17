package com.depot.shopping.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration("domainSecurityConfig")  // @Configuration 클래스를 등록할 때 기본적으로 클래스명을 Bean 이름으로 사용, 클래스가 두 개(api, domain) 있어서 충돌 발생
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}