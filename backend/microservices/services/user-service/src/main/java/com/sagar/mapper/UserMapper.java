package com.sagar.mapper;


import com.sagar.model.User;
import com.sagar.payload.dto.UserDto;

import java.time.LocalDateTime;

public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null)
            return null;

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .build();
    }

    public static User toEntity(UserDto req) {
        if (req == null)
            return null;

        return User.builder()
                .email(req.getEmail())
                .fullName(req.getFullName())
                .phone(req.getPhone())
                .role(req.getRole())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}

