package com.quizapp.dto.test;

import java.util.List;

public record TestResponse(
        String id,
        String title,
        String description,
        Long time,
        List questionIds
) {
}
