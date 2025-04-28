package com.quizapp.dto.user;

import jakarta.validation.constraints.NotBlank;
public record UserLoginRequest(

    @NotBlank(message = "Email is required")
    String email,

    @NotBlank(message = "Password is required")
    String password
)
{}
