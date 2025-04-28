package com.quizapp.controller;

import com.quizapp.dto.test.QuestionCreateRequest;
import com.quizapp.dto.test.QuestionResponse;
import com.quizapp.dto.test.SubmitTestRequest;
import com.quizapp.dto.test.TestCreateRequest;
import com.quizapp.entities.Question;
import com.quizapp.entities.Test;
import com.quizapp.entities.TestResult;
import com.quizapp.entities.User;
import com.quizapp.exception.ResourceNotFoundException;
import com.quizapp.repository.UserRepository;
import com.quizapp.service.test.TestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test")
@CrossOrigin("*")
public class TestController {

    @Autowired
    private TestService testService;
     private UserRepository userRepository;
    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PostMapping()
    public ResponseEntity<?> createTest(@RequestBody TestCreateRequest dto) {
        try {
            return new ResponseEntity<>(testService.createTest(dto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/questions")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionCreateRequest dto) {
        try {
            return new ResponseEntity<>(testService.addQuestion(dto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*  @GetMapping()
      public ResponseEntity<?> getAllTest(){
          try {
              return new ResponseEntity<>(testService.getAllTests(), HttpStatus.OK);

          }
          catch(Exception e){
              return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
          }

      }*/

    @GetMapping
    public ResponseEntity<?> getAllTest() {
        try {
            return new ResponseEntity<>(testService.getAllTests(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTestById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(testService.getAllQuestionsByTest(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{testId}/submit")
    public ResponseEntity<TestResult> submitTest(
            @PathVariable String testId, // Accept the testId from the URL
            @RequestBody SubmitTestRequest request,
            @AuthenticationPrincipal String username
    ) {
        System.out.println("Authenticated User Email: " + username);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Use testId in the service layer to process the submission
        TestResult result = testService.submitTest(testId, request, user.getId());
        return ResponseEntity.ok(result);
    }


}

