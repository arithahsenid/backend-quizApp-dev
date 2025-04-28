package com.quizapp.service.user;

import com.quizapp.Security.JwtUtil; // Add this import
import com.quizapp.dto.mapper.UserMapper;
import com.quizapp.dto.user.*;
import com.quizapp.entities.User;
import com.quizapp.enums.UserRole;
import com.quizapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil; // Add JwtUtil dependency

    // Updated constructor with JwtUtil
    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse createUser(UserSignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists!");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setName(request.name());
        user.setRole(request.role() != null
                ? UserRole.valueOf(request.role().toUpperCase())
                : UserRole.ADMIN);

        userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

    public AuthResponse loginUser(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                token // Include token in response
        );
    }
}