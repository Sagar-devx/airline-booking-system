package com.sagar.service;

import com.sagar.model.User;
import com.sagar.payload.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserByEmail(String email) throws Exception;

    UserDto getUserById(Long id) throws Exception;

    List<UserDto> getAllUsers();
}
