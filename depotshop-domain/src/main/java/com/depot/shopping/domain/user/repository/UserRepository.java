package com.depot.shopping.domain.user.repository;

import com.depot.shopping.domain.user.entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author DongMin Kim
 */
@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long save(Users users) {
        em.persist(users);
        return users.getSeqId();
    }

    @Transactional(readOnly = true)
    public Users find(Long seqId) {
        return em.find(Users.class, seqId);
    }
}
