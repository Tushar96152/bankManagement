package com.Banking.backend.service;

import com.Banking.backend.dto.request.CreateAccountRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreateAccountResponse;
import com.Banking.backend.entity.Card;

public interface BankAccountService {

    ApiResponse<CreateAccountResponse> createBankAccount(CreateAccountRequest createAccountRequest);
    String generateUniqueAccountNumber();
    Card generateDebitCard(String accountNumber);
    String generateUniqueUserNetId();
    ApiResponse<CreateAccountResponse> bankAccountByUserId(Long userId);
}
