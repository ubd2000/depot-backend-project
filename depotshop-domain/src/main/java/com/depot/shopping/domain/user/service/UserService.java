package com.depot.shopping.domain.user.service;

import com.depot.shopping.domain.user.entity.Users;
import com.depot.shopping.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author DongMin Kim
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Object find(Long seqId) {
        return Optional.ofNullable(userRepository.find(seqId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Long save(Users users) {
        return userRepository.save(users);
    }
}
