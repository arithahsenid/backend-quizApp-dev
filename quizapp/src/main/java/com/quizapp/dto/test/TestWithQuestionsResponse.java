package com.quizapp.dto.test;

import java.util.List;

public record TestWithQuestionsResponse(
        TestResponse test,
        List<QuestionResponse> questions
) {}
