package com.quizapp.repository;

import com.quizapp.dto.test.TestCreateRequest;
import com.quizapp.dto.test.TestResponse;
import com.quizapp.entities.Test;
import jakarta.validation.Valid;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TestRepository extends MongoRepository<Test, String> {

    Optional<Test> findById(String id);

}
