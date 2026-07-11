package com.sagar.service;

import com.sagar.payload.dto.UserDto;
import com.sagar.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String email, String password) throws Exception;

    AuthResponse signup(UserDto req) throws Exception;
}
