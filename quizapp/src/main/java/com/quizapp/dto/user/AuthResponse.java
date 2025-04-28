package com.quizapp.dto.user;
public record AuthResponse(
        String id,
        String name,
        String email,
        String role,
        String token // New token field
) {}
