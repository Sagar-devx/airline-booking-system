package com.sagar.service.impl;

import com.sagar.config.JwtProvider;
import com.sagar.enums.UserRole;
import com.sagar.mapper.UserMapper;
import com.sagar.model.User;
import com.sagar.payload.dto.UserDto;
import com.sagar.payload.response.AuthResponse;
import com.sagar.repository.UserRepository;
import com.sagar.service.AuthService;
import com.sagar.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthResponse signup(UserDto req) throws Exception {

        User existingUser = userRepository.findByEmail(req.getEmail());

        if (existingUser != null) {
            throw new Exception("User with email " + req.getEmail() + " already exists");
        }
        if (req.getRole() == UserRole.ROLE_SYSTEM_ADMIN) {
            throw new Exception("You cannot create a user with role SYSTEM_ADMIN");
        }
        User newUser = UserMapper.toEntity(req);
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));

        User savedUser = userRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(),
                savedUser.getPassword());

        String jwt = jwtProvider.generateJwtToken(authentication, savedUser.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDto(savedUser));
        authResponse.setTitle("Welcome " + savedUser.getFullName());
        authResponse.setMessage("Register Successfully");

        return authResponse;
    }

    @Override
    public AuthResponse login(String email, String password) throws Exception {
        Authentication authentication = authenticate(email, password);

        User user = userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String jwt = jwtProvider.generateJwtToken(authentication, user.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDto(user));
        authResponse.setTitle("Welcome " + user.getFullName());
        authResponse.setMessage("Login Successfully");
        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new Exception("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
    }
}

