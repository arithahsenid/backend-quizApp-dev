package com.quizapp.dto.test;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;


public record SubmitTestRequest(
        @NotBlank(message = "Test ID is required")
        String testId,

        @NotEmpty(message = "Answers cannot be empty")
        List<AnswerSubmission> answers
) {
    public record AnswerSubmission(
            @NotBlank(message = "Question ID is required")
            String questionId,

            @NotBlank(message = "Selected option is required")
            String selectedOption
    ) {}
}