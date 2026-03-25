package com.salon.repository;

import com.salon.model.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findTopByPhoneOrderByExpiryTimeDesc(String phone);

    Optional<OtpVerification> findTopByEmailOrderByExpiryTimeDesc(String email);
}
