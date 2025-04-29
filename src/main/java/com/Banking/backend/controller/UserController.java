package com.Banking.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.request.UserLoginPasswordChange;
import com.Banking.backend.dto.request.UserLoginRequest;
import com.Banking.backend.dto.request.UserRegisterRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.UserResponse;
import com.Banking.backend.repository.ServiceAccessor;


import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(
            @RequestBody UserLoginRequest userLoginRequest) {
            return ServiceAccessor.getUserService().login(userLoginRequest);
    }
    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody UserRegisterRequest userRegisterRequest){
        return ServiceAccessor.getUserService().register(userRegisterRequest);
    }

    @GetMapping("getById/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable ("id") Long userId){
        return ServiceAccessor.getUserService().getUserById(userId);
    }
    @PutMapping("/password-change")
    public ApiResponse<?> loginPasswordChange(@RequestBody UserLoginPasswordChange request){
        return ServiceAccessor.getUserService().passwordChange(request);
    }
}
