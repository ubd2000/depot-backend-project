package com.depot.shopping.domain.user.repository;

import com.depot.shopping.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사용자 조회(임시)
 */
@Repository
public interface testUserRepository extends JpaRepository<Users, String> {
    Users findByUserId(String userId);

    Users findBySeqId(long seqId);
}
