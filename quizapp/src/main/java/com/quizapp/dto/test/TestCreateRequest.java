package com.quizapp.dto.test;

public record TestCreateRequest(
        String title,
        String description,
        Long time
) {
}
