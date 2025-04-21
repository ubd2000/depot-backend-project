package com.depot.shopping.domain.user.repository;

import com.depot.shopping.domain.user.entity.SnsUsers;
import com.depot.shopping.domain.user.entity.SnsUsersMpng;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 회원 맵핑 정보 조회
 */
@Repository
public interface testSnsUserMpngRepository extends JpaRepository<SnsUsersMpng, String> {
    @EntityGraph(attributePaths = {"users", "snsUsers"})  // (최적화용) JPA 에서 추가 쿼리 없이 users까지 한 번에 가져오기, 없으면 .getUsers() 실행 시 쿼리 조회 나감.
    SnsUsersMpng findBySnsUsers(SnsUsers snsUsers);
}
