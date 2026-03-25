package com.salon.service;

import com.salon.config.JwtTokenProvider;
import com.salon.dto.*;
import com.salon.model.Client;
import com.salon.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Register a new Client
     */
    public SignupResponse registerClient(ClientSignupRequest request) {
        // Check if email already exists
        if (clientRepository.existsByEmail(request.getEmail())) {
            return new SignupResponse(
                    false,
                    "Email already registered",
                    null,
                    null
            );
        }

        try {
            Client client = new Client();
            client.setName(request.getName());
            client.setEmail(request.getEmail());
            client.setPassword(passwordEncoder.encode(request.getPassword()));
            client.setPhone(request.getPhone());

            Client savedClient = clientRepository.save(client);

            String token = jwtTokenProvider.generateToken(savedClient.getId(), savedClient.getEmail(), "client");

            UserDto userDto = new UserDto(
                    savedClient.getId(),
                    savedClient.getName(),
                    savedClient.getEmail(),
                    savedClient.getPhone(),
                    "client",
                    null,
                    null, token
            );

            return new SignupResponse(
                    true,
                    "Client registered successfully",
                    token,
                    userDto
            );
        } catch (Exception e) {
            return new SignupResponse(
                    false,
                    "Error registering client: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    /**
     * Login Client
     */
    public LoginResponse loginClient(String email, String password) {
        Optional<Client> clientOpt = clientRepository.findByEmail(email);

        if (clientOpt.isEmpty()) {
            return new LoginResponse(
                    false,
                    "Client not found with email: " + email,
                    null,
                    null
            );
        }

        Client client = clientOpt.get();

        if (!client.getActive()) {
            return new LoginResponse(
                    false,
                    "Client account is inactive",
                    null,
                    null
            );
        }

        if (!passwordEncoder.matches(password, client.getPassword())) {
            return new LoginResponse(
                    false,
                    "Invalid password",
                    null,
                    null
            );
        }

        String token = jwtTokenProvider.generateToken(client.getId(), client.getEmail(), "client");

        UserDto userDto = new UserDto(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                "client",
                null,
                null, token
        );

        return new LoginResponse(
                true,
                "Login successful",
                token,
                userDto
        );
    }

    /**
     * Get Client by ID
     */
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findByIdAndActiveTrue(id);
    }

    /**
     * Update Client Profile
     */
    public Client updateClient(Long id, Client clientDetails) {
        return clientRepository.findById(id).map(client -> {
            client.setName(clientDetails.getName());
            client.setPhone(clientDetails.getPhone());
            return clientRepository.save(client);
        }).orElse(null);
    }
}
