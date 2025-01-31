package com.example.whenwhen.repository;

import com.example.whenwhen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoSub(String kakaoSub);
    boolean existsByKakaoSub(String kakaoSub);
}
