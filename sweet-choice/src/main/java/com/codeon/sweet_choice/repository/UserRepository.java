package com.codeon.sweet_choice.repository;

import com.codeon.sweet_choice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(Long userId);
}
