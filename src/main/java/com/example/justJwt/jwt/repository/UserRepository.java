package com.example.justJwt.jwt.repository;

import com.example.justJwt.jwt.domain.User;
import com.example.justJwt.jwt.dto.UserSignUpDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    Optional<User> findByAccessToken(String token);
    Optional<User> findByNickname(String nickname);
}
