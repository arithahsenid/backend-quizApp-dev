package com.quizapp.dto.test;

// QuestionCreateRequest.java
public record QuestionCreateRequest(
        String questionText,
        String optionA,
        String optionB,
        String optionC,
        String optionD,
        String correctOption,
        String testId // Match Angular payload
) {}
