package com.salon.service;

import com.salon.config.JwtTokenProvider;
import com.salon.dto.*;
import com.salon.model.Owner;
import com.salon.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Register a new Owner
     */
    public SignupResponse registerOwner(OwnerSignupRequest request) {
        // Check if email already exists
        if (ownerRepository.existsByEmail(request.getEmail())) {
            return new SignupResponse(
                    false,
                    "Email already registered",
                    null,
                    null
            );
        }

        try {
            Owner owner = new Owner();
            owner.setName(request.getName());
            owner.setEmail(request.getEmail());
            owner.setPassword(passwordEncoder.encode(request.getPassword()));
            owner.setPhone(request.getPhone());
            owner.setSalonName(request.getSalonName());
            owner.setSalonType(request.getSalonType().toLowerCase());
            owner.setAddress(request.getAddress());

            Owner savedOwner = ownerRepository.save(owner);

            String token = jwtTokenProvider.generateToken(savedOwner.getId(), savedOwner.getEmail(), "owner");

            UserDto userDto = new UserDto(
                    savedOwner.getId(),
                    savedOwner.getName(),
                    savedOwner.getEmail(),
                    savedOwner.getPhone(),
                    "owner",
                    savedOwner.getSalonName(),
                    savedOwner.getSalonType(),
                    savedOwner.getAddress()
            );

            return new SignupResponse(
                    true,
                    "Owner registered successfully",
                    token,
                    userDto
            );
        } catch (Exception e) {
            return new SignupResponse(
                    false,
                    "Error registering owner: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    /**
     * Login Owner
     */
    public LoginResponse loginOwner(String email, String password) {
        Optional<Owner> ownerOpt = ownerRepository.findByEmail(email);

        if (ownerOpt.isEmpty()) {
            return new LoginResponse(
                    false,
                    "Owner not found with email: " + email,
                    null,
                    null
            );
        }

        Owner owner = ownerOpt.get();

        if (!owner.getActive()) {
            return new LoginResponse(
                    false,
                    "Owner account is inactive",
                    null,
                    null
            );
        }

        if (!passwordEncoder.matches(password, owner.getPassword())) {
            return new LoginResponse(
                    false,
                    "Invalid password",
                    null,
                    null
            );
        }

        String token = jwtTokenProvider.generateToken(owner.getId(), owner.getEmail(), "owner");

        UserDto userDto = new UserDto(
                owner.getId(),
                owner.getName(),
                owner.getEmail(),
                owner.getPhone(),
                "owner",
                owner.getSalonName(),
                owner.getSalonType(),
                owner.getAddress()
        );

        return new LoginResponse(
                true,
                "Login successful",
                token,
                userDto
        );
    }

    /**
     * Get Owner by ID
     */
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findByIdAndActiveTrue(id);
    }

    /**
     * Update Owner Profile
     */
    public Owner updateOwner(Long id, Owner ownerDetails) {
        return ownerRepository.findById(id).map(owner -> {
            owner.setName(ownerDetails.getName());
            owner.setPhone(ownerDetails.getPhone());
            owner.setSalonName(ownerDetails.getSalonName());
            owner.setAddress(ownerDetails.getAddress());
            return ownerRepository.save(owner);
        }).orElse(null);
    }
}
