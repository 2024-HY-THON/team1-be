package com.example.teamOnebe.repository;

import com.example.teamOnebe.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndCode(String email, Long code);
}
