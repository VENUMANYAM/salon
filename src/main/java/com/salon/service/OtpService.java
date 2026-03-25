package com.salon.service;

import com.salon.model.OtpVerification;
import com.salon.repository.OtpVerificationRepository;
import com.salon.util.OTPUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final TwilioService twilioService;
    private final EmailService emailService;
    private final OtpVerificationRepository otpRepository;

    private static final int OTP_EXPIRY_MINUTES = 5;

    public void sendSmsOtp(String phone) {
        String otp = OTPUtil.generateOtp();
        saveOtp(phone, null, otp);
        twilioService.sendOtp(phone, otp);
    }

    public void sendEmailOtp(String email) {
        String otp = OTPUtil.generateOtp();
        saveOtp(null, email, otp);
        emailService.sendEmail(email, "Your OTP Code", "Your OTP is: " + otp);
    }

    public boolean verifySmsOtp(String phone, String otp) {
        return verifyOtp(phone, null, otp);
    }

    public boolean verifyEmailOtp(String email, String otp) {
        return verifyOtp(null, email, otp);
    }

    private void saveOtp(String phone, String email, String otp) {
        OtpVerification entity = new OtpVerification();
        entity.setPhone(phone);
        entity.setEmail(email);
        entity.setOtp(otp);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        otpRepository.save(entity);
    }

    private boolean verifyOtp(String phone, String email, String otp) {
        OtpVerification stored;
        if (phone != null) {
            stored = otpRepository.findTopByPhoneOrderByExpiryTimeDesc(phone).orElse(null);
        } else {
            stored = otpRepository.findTopByEmailOrderByExpiryTimeDesc(email).orElse(null);
        }

        if (stored == null) {
            return false;
        }

        if (stored.getExpiryTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        return stored.getOtp().equals(otp);
    }
}
