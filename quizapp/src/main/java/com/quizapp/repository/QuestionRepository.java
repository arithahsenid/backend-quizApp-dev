package com.quizapp.repository;

import com.quizapp.entities.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String>

{
    List<Question> findByTestIdOrderByQuestionOrderAsc(String testId);
    List<Question> findByTestId(String testId);
}
