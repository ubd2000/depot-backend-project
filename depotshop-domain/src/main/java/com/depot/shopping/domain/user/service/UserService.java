package com.depot.shopping.domain.user.service;

import com.depot.shopping.domain.user.entity.SnsUsers;
import com.depot.shopping.domain.user.entity.SnsUsersMpng;
import com.depot.shopping.domain.user.entity.Users;
import com.depot.shopping.domain.user.repository.UserRepository;
import com.depot.shopping.domain.user.repository.testSnsUserRepository;
import com.depot.shopping.domain.user.repository.testUserRepository;
import com.depot.shopping.domain.user.repository.testSnsUserMpngRepository;
import com.depot.shopping.error.exception.CustomUserInsertException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author DongMin Kim
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final testUserRepository testUserRepository;
    private final testSnsUserRepository testSnsUserRepository;
    private final testSnsUserMpngRepository testSnsUserMpngRepository;

    public Object find(Long seqId) {
        return Optional.ofNullable(userRepository.find(seqId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Long save(Users users) {
        return userRepository.save(users);
    }

    /**
     * SNS 서버로부터 받은 고유 id로 회원가입
     */
    @Transactional
    public SnsUsersMpng snsSignUp(String id, String provider) {
        // Users result = null;
        SnsUsersMpng result = null;

        // 해당 조회된 고유 id로 회원여부 구분
        SnsUsers snsUser = testSnsUserRepository.findByOauthIdAndOauthProvider(id, provider);

        if (snsUser != null) {
            // 회원
            // 회원정보 리턴
            // SNS 회원 정보로 맵핑을 통해 실제 회원 정보 조회
            result = testSnsUserMpngRepository.findBySnsUsers(snsUser);
        } else {
            // 비회원
            try {
                Users newUser = Users.builder()
                        .role("user")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                // 회원 추가
                Users savedUser = testUserRepository.save(newUser);
                if (savedUser.getSeqId() <= 0) {
                    throw new CustomUserInsertException("USER_INSERT_ERROR");
                }

                SnsUsers newSnsUser = SnsUsers.builder()
                        .oauthId(id)
                        .oauthProvider(provider)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                // 회원등록 후 SNS 계정 정보 추가
                SnsUsers savedSnsUser = testSnsUserRepository.save(newSnsUser);
                if (savedSnsUser.getSeqId() <= 0) {
                    throw new CustomUserInsertException("USER_INSERT_ERROR");
                }

                SnsUsersMpng newMpng = SnsUsersMpng.builder()
                        .users(savedUser)
                        .snsUsers(savedSnsUser)
                        .build();

                // 회원과 SNS 회원계정 맵핑 정보 추가
                SnsUsersMpng savedMpng = testSnsUserMpngRepository.save(newMpng);
                if (savedMpng.getUsers() == null || savedMpng.getSnsUsers() == null) {
                    throw new CustomUserInsertException("USER_INSERT_ERROR");
                }

                result = savedMpng;
            } catch (Exception e) {
                // 등록실패
                throw new CustomUserInsertException("USER_INSERT_ERROR");
            }
        }

        return result;
    }
}
