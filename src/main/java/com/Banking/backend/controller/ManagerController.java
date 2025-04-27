package com.Banking.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.request.StatusChangeApplicationDTO;
import com.Banking.backend.dto.request.StatusChangeLoanDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreditCardApplicationResponseDTO;
import com.Banking.backend.dto.response.DashboardStatsDTO;
import com.Banking.backend.dto.response.LoanResponseDTO;
import com.Banking.backend.dto.response.UserResponse;
import com.Banking.backend.repository.ServiceAccessor;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @GetMapping("/dashboard")
    public ApiResponse<DashboardStatsDTO> getDashbordStatus(){
        return ServiceAccessor.getManagerService().getDashboardStats();
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUsers(){
        return ServiceAccessor.getManagerService().getAllUsers();
    }
    @GetMapping("/loans")
    public ApiResponse<List<LoanResponseDTO>> getAllLoans(){
        return ServiceAccessor.getManagerService().getAllLoans();
    }
    @GetMapping("/credit-card-app")
    public ApiResponse<List<CreditCardApplicationResponseDTO>> getAllCreditCardApplications(){
        return ServiceAccessor.getManagerService().getAllCreditCardApplications();
    }
    @PutMapping("/change-status-loan")
    public ApiResponse<?> loanstatusChange(@RequestBody StatusChangeLoanDTO reqest)
    {
        return ServiceAccessor.getManagerService().statusChangeLoan(reqest);
    }
    @PutMapping("/change-status-credit-card")
    public ApiResponse<?> creditCardStatusChange(@RequestBody StatusChangeApplicationDTO reqest)
    {
        return ServiceAccessor.getManagerService().statusChangeApp(reqest);
    }

}
