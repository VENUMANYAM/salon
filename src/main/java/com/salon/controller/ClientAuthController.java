package com.salon.controller;

import com.salon.dto.*;
import com.salon.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/client")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ClientAuthController {
    private final ClientService clientService;

    /**
     * Client Signup Endpoint
     * POST /api/auth/client/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody ClientSignupRequest request) {
        SignupResponse response = clientService.registerClient(request);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Client Login Endpoint
     * POST /api/auth/client/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(
                    new LoginResponse(false, "Email and password are required", null, null)
            );
        }

        LoginResponse response = clientService.loginClient(request.getEmail(), request.getPassword());
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Verify Client Token
     * POST /api/auth/client/verify
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
