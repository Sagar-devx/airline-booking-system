package com.sagar.controller;

import com.sagar.payload.dto.UserDto;
import com.sagar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserByProfile(
            @RequestHeader("X-User-Email") String email
    ) throws Exception {
        log.info("Request to get user profile for email: {}", email);
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Long userId
    ) throws Exception {
        log.info("Request to get user by id: {}", userId);
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Request to get all users");
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
