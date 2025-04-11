package com.Banking.backend.service;

import com.Banking.backend.dto.request.CreateAccountRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreateAccountResponse;

public interface BankAccountService {

    ApiResponse<CreateAccountResponse> createBankAccount(CreateAccountRequest createAccountRequest);

}
