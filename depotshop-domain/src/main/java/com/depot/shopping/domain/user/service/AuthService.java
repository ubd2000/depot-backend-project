package com.depot.shopping.domain.user.service;

import com.depot.shopping.domain.TokenService;
import com.depot.shopping.domain.user.entity.JwtDTO;
import com.depot.shopping.domain.user.entity.Users;
import com.depot.shopping.domain.user.repository.testUserRepository;
import com.depot.shopping.error.exception.CustomUserNotFoundException;
import com.depot.shopping.error.exception.CustomUserWrongPwdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 로그인 처리
 */
@Service
@RequiredArgsConstructor  // ✅ Lombok을 활용한 생성자 주입
public class AuthService {

    private final testUserRepository testUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public JwtDTO login(Users loginUser) {

        Users user = Optional.ofNullable(testUserRepository.findByUserId(loginUser.getUserId()))
                .orElseThrow(() -> new CustomUserNotFoundException("User not found"));

        // 입력한 비밀번호와 DB의 해시된 비밀번호 비교 / 임시데이터 (암호화X 기준이라 한번 가공)
        if (!passwordEncoder.matches(loginUser.getUserPasswd(), passwordEncoder.encode(user.getUserPasswd()))) {
            throw new CustomUserWrongPwdException("Invalid password");
        }

        // 로그인 성공 시 JWT 토큰 발급
        JwtDTO jwt = new JwtDTO();
        jwt.setAccessToken(tokenService.generateAccessToken(loginUser.getUserId()));
        jwt.setRefreshToken(tokenService.generateRefreshToken(loginUser.getUserId()));

        // 발급받은 토큰 기준으로 redis에 등록하면될듯?

         return jwt;
    }
}