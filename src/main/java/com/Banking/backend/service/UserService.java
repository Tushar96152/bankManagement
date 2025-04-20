package com.Banking.backend.service;


import com.Banking.backend.dto.request.UserLoginRequest;
import com.Banking.backend.dto.request.UserRegisterRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.UserResponse;



public interface UserService {

     ApiResponse<UserResponse> login(UserLoginRequest userLoginRequest);
     ApiResponse<UserResponse>  register(UserRegisterRequest userRegisterRequest);
     ApiResponse<UserResponse> getUserById(Long userId);
}
