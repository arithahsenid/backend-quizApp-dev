package com.quizapp.controller;

import com.quizapp.dto.user.*;
import com.quizapp.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // UserController.java
    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody UserSignUpRequest request) {
        try {
            UserResponse response = userService.createUser(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Registration failed: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody UserLoginRequest request) {
            AuthResponse response = userService.loginUser(request);
            return ResponseEntity.ok(response);
    }
}