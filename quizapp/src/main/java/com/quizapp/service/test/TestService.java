package com.quizapp.service.test;

import com.quizapp.dto.mapper.TestMapper;
import com.quizapp.dto.test.*;
import com.quizapp.entities.Question;
import com.quizapp.entities.Test;
import com.quizapp.entities.TestResult;
import com.quizapp.entities.User;
import com.quizapp.exception.ResourceNotFoundException;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.repository.TestRepository;
import com.quizapp.repository.TestResultRepository;
import com.quizapp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static com.quizapp.dto.mapper.TestMapper.toQuestionResponse;
import static com.quizapp.dto.mapper.TestMapper.toTestResponse;

@Service
@Validated
public class TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TestResultRepository testResultRepository;
    public TestService(TestRepository testRepository, QuestionRepository questionRepository, UserRepository userRepository, TestResultRepository testResultRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.testResultRepository = testResultRepository;
    }

    // TestService.java
    public TestResponse createTest(@Valid TestCreateRequest request) {
        Test test = new Test();
        test.setTitle(request.title());
        test.setDescription(request.description());
        test.setTime(request.time());
        test.setQuestionIds(new ArrayList<>()); // Explicit initialization

        testRepository.save(test);
        return toTestResponse(test);
    }

    // TestService.java
    public QuestionResponse addQuestion(@Valid QuestionCreateRequest request) {
        Test test = testRepository.findById(request.testId())
                .orElseThrow(() -> new ResourceNotFoundException("Test not found!"));

        Question question = new Question();
        question.setQuestionText(request.questionText());
        question.setOptionA(request.optionA());
        question.setOptionB(request.optionB());
        question.setOptionC(request.optionC());
        question.setOptionD(request.optionD());
        question.setCorrectOption(request.correctOption());
        question.setTestId(request.testId());
        question.setQuestionOrder(test.getQuestionIds().size() + 1);

        questionRepository.save(question);
        test.getQuestionIds().add(question.getId());
        testRepository.save(test);

        return toQuestionResponse(question);
    }


    public TestWithQuestionsResponse getTestWithQuestions(String testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + testId));

        List<Question> questions = questionRepository.findByTestId(testId);
        List<QuestionResponse> questionResponses = questions.stream()
                .map(TestMapper::toQuestionResponse)
                .sorted(Comparator.comparingInt(QuestionResponse::questionOrder))
                .toList();

        return new TestWithQuestionsResponse(
                toTestResponse(test),
                questionResponses
        );
    }

    public List<TestResponse> getAllTests() {
        return testRepository.findAll().stream()
                .map(TestMapper::toTestResponse)
                .toList();
    }

    // Helper method
    private int getNextQuestionOrder(String testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found!"));
        return test.getQuestionIds().size() + 1;
    }

    public TestWithQuestionsResponse getAllQuestionsByTest(String testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + testId));

        // Get questions sorted by questionOrder
        List<Question> questions = questionRepository.findByTestIdOrderByQuestionOrderAsc(testId);

        // Convert to DTOs
        TestResponse testResponse = TestMapper.toTestResponse(test);
        List<QuestionResponse> questionResponses = questions.stream()
                .map(TestMapper::toQuestionResponse)
                .toList();

        return new TestWithQuestionsResponse(testResponse, questionResponses);
    }
    // Add this method
    public TestResult submitTest(String testId, SubmitTestRequest request, String userId) {
        // Get test and user
        Test test = testRepository.findById(request.testId())
                .orElseThrow(() -> new ResourceNotFoundException("Test not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Get all test questions
        List<Question> questions = questionRepository.findByTestId(request.testId());

        // Calculate correct answers
        long correctAnswers = request.answers().stream()
                .filter(answer -> {
                    Question question = questions.stream()
                            .filter(q -> q.getId().equals(answer.questionId()))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
                    return question.getCorrectOption().equals(answer.selectedOption());
                })
                .count();

        // Calculate results
        int totalQuestions = questions.size();
        double percentage = (double) correctAnswers / totalQuestions * 100;

        // Create and save test result
        TestResult testResult = new TestResult();
        testResult.setTest(test);
        testResult.setUser(user);
        testResult.setTotalQuestions(totalQuestions);
        testResult.setCorrectAnswers((int) correctAnswers);
        testResult.setPercentage(percentage);
        testResult.setCreatedAt(LocalDateTime.now());

        return testResultRepository.save(testResult);
    }
}

