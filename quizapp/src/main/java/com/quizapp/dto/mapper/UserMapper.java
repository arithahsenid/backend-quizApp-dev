package com.quizapp.dto.mapper;

import com.quizapp.entities.User;
import com.quizapp.dto.user.UserResponse;
public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
