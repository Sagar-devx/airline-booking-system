package com.sagar.payload.response;

import com.sagar.payload.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String jwt;
    private String message;
    private String title;
    private UserDto user;
}
