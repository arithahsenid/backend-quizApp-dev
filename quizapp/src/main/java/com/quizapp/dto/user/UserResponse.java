package com.quizapp.dto.user;

public record UserResponse (
    String id,
    String name,
    String email,
    String role
){}
