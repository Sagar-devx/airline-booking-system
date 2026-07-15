package com.sagar.controller;

import com.sagar.payload.dto.UserDto;
import com.sagar.payload.request.LoginRequest;
import com.sagar.payload.response.AuthResponse;
import com.sagar.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid UserDto userDto) throws Exception {
        log.info("Request to signup user: {}", userDto.getEmail());
        AuthResponse authResponse = authService.signup(userDto);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        log.info("Request to login user: {}", loginRequest.getEmail());
        AuthResponse authResponse = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(authResponse);
    }
}
