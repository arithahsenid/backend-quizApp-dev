package com.quizapp.dto.test;

public record QuestionResponse(
        String id,
        String questionText,
        String optionA,
        String optionB,
        String optionC,
        String optionD,
        String correctOption,
        String testId,
        Integer questionOrder
) {
    @Override
    public String id() {
        return id;
    }

    @Override
    public String questionText() {
        return questionText;
    }

    @Override
    public String optionA() {
        return optionA;
    }

    @Override
    public String optionB() {
        return optionB;
    }

    @Override
    public String optionC() {
        return optionC;
    }

    @Override
    public String optionD() {
        return optionD;
    }

    @Override
    public String testId() {
        return testId;
    }

    @Override
    public String correctOption() {
        return correctOption;
    }

    @Override
    public Integer questionOrder() {
        return questionOrder;
    }
}
