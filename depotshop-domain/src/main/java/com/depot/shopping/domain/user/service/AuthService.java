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

import java.time.Instant;
import java.util.Map;
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

    public Map<String, Object> login(Users loginUser) {
        Map<String, Object> result;
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

        long expiresIn = 3600; // 1시간 (초 단위)

        // accessToken	사용자가 API 요청 시 사용하는 JWT 액세스 토큰	✅ 필수
        // refreshToken	액세스 토큰이 만료되었을 때 재발급 받기 위한 토큰	✅ 필수
        // expiresIn	액세스 토큰 만료 시간 (초 단위)	✅ 필수
        // tokenType	일반적으로 Bearer로 설정	✅ 필수
        // userId	로그인한 사용자의 고유 식별 ID	✅ 필수
        // username	사용자 계정명 (아이디)	✅ 필수
        // roles	사용자의 역할 (ex: USER, ADMIN)	✅ 필수
        // permissions	사용자가 FO/BO에서 수행할 수 있는 권한 리스트 (ex: READ_USERS, MANAGE_ORDERS)	⚠️ 선택
        // issuedAt	토큰 발급 시간 (ISO 8601 포맷)	⚠️ 선택
        // expiresAt 토큰 만료 시간 (ISO 8601 포맷) ⚠️ 선택
        result = Map.of(
                "accessToken", jwt.getAccessToken(),
                "refreshToken", jwt.getRefreshToken(),
                "expiresIn", expiresIn,
                "tokenType", "Bearer",
                "userId", user.getUserId(),
                "userName", user.getUserName(),
                "role", user.getRole(),
                "issuedAt", Instant.now().toString(),
                "expiresAt", Instant.now().plusSeconds(expiresIn).toString()
        );

        // 발급받은 토큰 기준으로 redis에 등록하면될듯?

        return result;
    }
}