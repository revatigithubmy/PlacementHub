package com.placementhub.backend.service;

import com.placementhub.backend.dto.AuthResponse;
import com.placementhub.backend.dto.LoginRequest;
import com.placementhub.backend.dto.RegisterRequest;
import com.placementhub.backend.dto.RegisterResponse;
import com.placementhub.backend.entity.User;
import com.placementhub.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // =========================
    // REGISTER
    // =========================

    public RegisterResponse register(
            RegisterRequest request
    ) {

        // Check if email already exists
        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        // Create new user
        User user = new User();

        user.setEmail(
                request.getEmail()
        );

        // Encrypt password using BCrypt
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(
                request.getRole()
        );

        // Save user
        User savedUser =
                userRepository.save(user);

        // Return registration response
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole(),
                "Registration successful"
        );
    }


    // =========================
    // LOGIN
    // =========================

    public AuthResponse login(
            LoginRequest request
    ) {

        // Find user by email
        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid email or password"
                                )
                        );

        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        // Generate JWT
        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole().name()
                );

        // Return login response
        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}