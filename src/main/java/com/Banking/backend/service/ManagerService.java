package com.Banking.backend.service;

import java.util.List;

import com.Banking.backend.dto.request.StatusChangeApplicationDTO;
import com.Banking.backend.dto.request.StatusChangeLoanDTO;
import com.Banking.backend.dto.request.UserRegisterRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreditCardApplicationResponseDTO;

import com.Banking.backend.dto.response.DashboardStatsDTO;
import com.Banking.backend.dto.response.LoanResponseDTO;
import com.Banking.backend.dto.response.UserResponse;


public interface ManagerService {

     ApiResponse<DashboardStatsDTO> getDashboardStats();
     ApiResponse<List<UserResponse>> getAllUsers();
     ApiResponse<UserResponse> addUser(UserRegisterRequest request);
     ApiResponse<List<LoanResponseDTO>> getAllLoans();
     ApiResponse<List<CreditCardApplicationResponseDTO>> getAllCreditCardApplications();
     ApiResponse<?> statusChangeLoan(StatusChangeLoanDTO request);
     ApiResponse<?> statusChangeApp(StatusChangeApplicationDTO request);
     

}
