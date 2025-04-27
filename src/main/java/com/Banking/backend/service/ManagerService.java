package com.Banking.backend.service;

import com.Banking.backend.dto.request.UserRegisterRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.DashboardStatsDTO;
import com.Banking.backend.dto.response.LoanResponseDTO;
import com.Banking.backend.dto.response.UserResponse;


public interface ManagerService {

     ApiResponse<DashboardStatsDTO> getDashboardStats();
     ApiResponse<UserResponse> getAllUsers();
     ApiResponse<UserResponse> addUser(UserRegisterRequest request);
     ApiResponse<LoanResponseDTO> getAllLoans();

}
