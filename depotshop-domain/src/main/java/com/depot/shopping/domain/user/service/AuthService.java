package com.depot.shopping.domain.user.service;

import com.depot.shopping.domain.user.entity.Users;
import com.depot.shopping.domain.user.repository.testUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 로그인 처리
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private testUserRepository testUserRepository;
    //private final PasswordEncoder passwordEncoder;

    public String login(Users loginUser) {

        Users user = Optional.ofNullable(testUserRepository.findByUserId(loginUser.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

//        // 입력한 비밀번호와 DB의 해시된 비밀번호 비교
//        if (!passwordEncoder.matches(loginUser.getUserPasswd(), user.getUserPasswd())) {
//            throw new BadCredentialsException("Invalid password");
//        }

        // 로그인 성공 시 JWT 토큰 발급
        return "test_token_setting";
    }
}