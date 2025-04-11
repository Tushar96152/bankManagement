package com.Banking.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.request.UserLoginRequest;
import com.Banking.backend.dto.request.UserRegisterRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.UserResponse;
import com.Banking.backend.entity.User;
import com.Banking.backend.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

   
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(
            @RequestBody UserLoginRequest userLoginRequest) {
            return userService.login(userLoginRequest);
    }
    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody UserRegisterRequest userRegisterRequest)
    {
        return userService.register(userRegisterRequest);
    }
}
