package com.sagar.service.impl;

import com.sagar.mapper.UserMapper;
import com.sagar.model.User;
import com.sagar.payload.dto.UserDto;
import com.sagar.repository.UserRepository;
import com.sagar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserByEmail(String email) throws Exception {
        log.info("Fetching user by email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) throws Exception {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Fetching all registered users");
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}
