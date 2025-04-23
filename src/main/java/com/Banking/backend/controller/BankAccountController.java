package com.Banking.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.request.AccountLoginRequest;
import com.Banking.backend.dto.request.CreateAccountRequest;
import com.Banking.backend.dto.request.LoginNetPasswordChange;
import com.Banking.backend.dto.request.TransactionPasswordChange;
import com.Banking.backend.dto.response.AccountLoginResponse;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreateAccountResponse;

import com.Banking.backend.repository.ServiceAccessor;

@RestController
@RequestMapping("/account")
public class BankAccountController {


    @PostMapping("/create")
    public ApiResponse<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request){
        return ServiceAccessor.getBankAccountService().createBankAccount(request);
    }
    @GetMapping("getById/{id}")
    public ApiResponse<CreateAccountResponse> getUserById(@PathVariable ("id") Long userId){
        return ServiceAccessor.getBankAccountService().bankAccountByUserId(userId);
    }

    @PostMapping("/login")
    public ApiResponse<AccountLoginResponse> login(@RequestBody AccountLoginRequest loginRequest)
    {
        return ServiceAccessor.getBankAccountService().login(loginRequest);
    }
    @PutMapping("/login-password-change")
    public ApiResponse<?> loginPasswordChange(@RequestBody LoginNetPasswordChange request){
        return ServiceAccessor.getBankAccountService().loginPasswordChange(request);
    }
    @PutMapping("/transaction-password-change")
    public ApiResponse<?> transactionPasswordChange(@RequestBody TransactionPasswordChange request){
        return ServiceAccessor.getBankAccountService().transactionPasswordChange(request);
    }
}
