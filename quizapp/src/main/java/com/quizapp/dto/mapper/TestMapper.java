package com.quizapp.dto.mapper;

import com.quizapp.entities.Test;
import com.quizapp.entities.Question;
import com.quizapp.dto.test.*;
public class TestMapper {
    public static TestResponse toTestResponse(Test test) {
        return new TestResponse(
                test.getId(),
                test.getTitle(),
                test.getDescription(),
                test.getTime(),
                test.getQuestionIds() // Ensure this returns the list of question IDs
        );
    }

    public static QuestionResponse toQuestionResponse(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getQuestionText(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getCorrectOption(),
                question.getTestId(),
                question.getQuestionOrder()
        );
    }
}
