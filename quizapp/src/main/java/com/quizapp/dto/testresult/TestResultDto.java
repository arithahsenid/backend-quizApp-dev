// TestResultDto.java
package com.quizapp.dto.testresult;

import com.quizapp.dto.test.TestResponse;
import com.quizapp.dto.user.UserResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.quizapp.entities.TestResult;

import java.time.LocalDateTime;

public record TestResultDto(
        String id,
        int totalQuestions,
        int correctAnswers,
        double percentage,
        TestResponse test,
        UserResponse user,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt
) {
    // Mapper method using your existing DTOs
    public static TestResultDto fromEntity(
            TestResult entity,
            TestResponse testResponse,
            UserResponse userResponse
    ) {
        return new TestResultDto(
                entity.getId(),
                entity.getTotalQuestions(),
                entity.getCorrectAnswers(),
                entity.getPercentage(),
                testResponse,
                userResponse,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}