package com.depot.shopping.domain.user.service;

import com.depot.shopping.domain.RedisService;
import com.depot.shopping.domain.TokenService;
import com.depot.shopping.domain.user.entity.JwtDTO;
import com.depot.shopping.domain.user.entity.Users;
import com.depot.shopping.domain.user.repository.testUserRepository;
import com.depot.shopping.error.exception.CustomInvalidRefreshTokenException;
import com.depot.shopping.error.exception.CustomUserNotFoundException;
import com.depot.shopping.error.exception.CustomUserWrongPwdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    private final RedisService redisService;

    /**
     * refreshToken 으로 새로운 accessToken 발급
     */
    public Map<String, Object> refreshTokenCheck(JwtDTO token) {
        Map<String, Object> result;
        String tokenKey = token.getRefreshToken();

        if(tokenService.validateToken(tokenKey, true)) {
            // 검증된 리프레시 토큰이면 엑세스토큰 재발급
            // 기존 토큰이 redis에 존재하면, 해당토큰 만료(삭제) 시킨 후 재발급 진행하면 될듯? (추가필요)

            // 토큰에서 userId 추출
            Authentication authInfo = tokenService.getAuthentication(tokenKey, true);
            String userId = authInfo.getName();

            // 추출한 userId로 정보 조회
            Users user = this.getUserInfo(userId);

            JwtDTO jwt = new JwtDTO();
            jwt.setAccessToken(tokenService.generateAccessToken(userId));
            jwt.setRefreshToken(tokenService.generateRefreshToken(userId));

            result = this.responseData(jwt, user);

            // 발급한 토큰을 redis 재등록 (추가필요)

        } else {
            throw new CustomInvalidRefreshTokenException("Invalid refreshToken");
        }

        return result;
    }

    public Map<String, Object> login(Users loginUser) {
        Map<String, Object> result;
        Users user = Optional.ofNullable(testUserRepository.findByUserId(loginUser.getUserId()))
                .orElseThrow(() -> new CustomUserNotFoundException("User not found"));

        // 입력한 비밀번호와 DB의 해시된 비밀번호 비교 / 임시데이터 (암호화X 기준이라 한번 가공)
        if (!passwordEncoder.matches(loginUser.getUserPasswd(), passwordEncoder.encode(user.getUserPasswd()))) {
            throw new CustomUserWrongPwdException("Invalid password");
        }

        // redis에 기존 토큰 있으면 만료(삭제) 처리
        // redisService.removeToken(user.getUserId());

        // 로그인 성공 시 JWT 토큰 발급
        JwtDTO jwt = new JwtDTO();
        jwt.setAccessToken(tokenService.generateAccessToken(loginUser.getUserId()));
        jwt.setRefreshToken(tokenService.generateRefreshToken(loginUser.getUserId()));

        result = this.responseData(jwt, user);
        // 발급받은 토큰 기준으로 redis에 등록하면될듯? (추가필요)

        return result;
    }

    /**
     * 토큰과, 유저 정보로 응답객체 생성
     * @return
     */
    private Map<String, Object> responseData(JwtDTO jwt, Users user) {
        Map<String, Object> map;

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
        map = Map.of(
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
        
        return map;
    }

    /**
     * userId로 유저정보 조회
     */
    private Users getUserInfo(String userId){
        Users user = Optional.ofNullable(testUserRepository.findByUserId(userId))
                .orElseThrow(() -> new CustomUserNotFoundException("User not found"));

        return user;
    }
}