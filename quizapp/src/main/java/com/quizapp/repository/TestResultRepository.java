package com.quizapp.repository;

import com.quizapp.entities.TestResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestResultRepository extends MongoRepository<TestResult,String> {

}
