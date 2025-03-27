package com.depot.shopping.domain.user.repository;

import com.depot.shopping.domain.user.entity.SnsUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사용자 조회(임시) : SNS 정보
 */
@Repository
public interface testSnsUserRepository extends JpaRepository<SnsUsers, String> {
    SnsUsers findByOauthId(String oauthId);
}
