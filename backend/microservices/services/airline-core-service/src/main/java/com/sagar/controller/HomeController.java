package com.sagar.controller;

import com.sagar.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController() {
        ApiResponse response = new ApiResponse("hey everyone, im airline core service");
        return response;
    }
}
