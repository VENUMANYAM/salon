package com.salon.controller;

import com.salon.dto.ApiResponse;
import com.salon.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OTPController {

    private final OtpService otpService;

    @PostMapping("/send-sms")
    public ApiResponse sendSmsOtp(@RequestParam String phone) {
        otpService.sendSmsOtp(phone);
        return new ApiResponse(true, "OTP sent to phone");
    }

    @PostMapping("/send-email")
    public ApiResponse sendEmailOtp(@RequestParam String email) {
        otpService.sendEmailOtp(email);
        return new ApiResponse(true, "OTP sent to email");
    }

    @PostMapping("/verify-sms")
    public ApiResponse verifySmsOtp(@RequestParam String phone, @RequestParam String otp) {
        boolean valid = otpService.verifySmsOtp(phone, otp);
        return new ApiResponse(valid, valid ? "OTP verified" : "Invalid or expired OTP");
    }

    @PostMapping("/verify-email")
    public ApiResponse verifyEmailOtp(@RequestParam String email, @RequestParam String otp) {
        boolean valid = otpService.verifyEmailOtp(email, otp);
        return new ApiResponse(valid, valid ? "OTP verified" : "Invalid or expired OTP");
    }
}
