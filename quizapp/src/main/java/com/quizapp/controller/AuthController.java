package com.quizapp.controller;

import com.quizapp.Security.JwtUtil;
import com.quizapp.entities.User;
import com.quizapp.repository.UserRepository; // Assuming you have this
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;


}
