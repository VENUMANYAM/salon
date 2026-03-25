package com.salon.controller;

import com.salon.dto.*;
import com.salon.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/owner")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OwnerAuthController {
    private final OwnerService ownerService;

    /**
     * Owner Signup Endpoint
     * POST /api/auth/owner/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody OwnerSignupRequest request) {
        SignupResponse response = ownerService.registerOwner(request);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Owner Login Endpoint
     * POST /api/auth/owner/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(
                    new LoginResponse(false, "Email and password are required", null, null)
            );
        }

        LoginResponse response = ownerService.loginOwner(request.getEmail(), request.getPassword());
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Verify Owner Token
     * POST /api/auth/owner/verify
     */
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verifyToken(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(false, "Invalid token format")
            );
        }

        // Token validation would be done here
        return ResponseEntity.ok(new ApiResponse(true, "Token is valid"));
    }
}
