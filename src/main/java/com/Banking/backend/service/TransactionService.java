package com.Banking.backend.service;

import com.Banking.backend.dto.request.TransactionRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.TransactionResponseDTO;

public interface TransactionService {

    ApiResponse<TransactionResponseDTO> depositeMoney(TransactionRequestDTO transactionRequestDTO);
    ApiResponse<TransactionResponseDTO> withDrawMoney(TransactionRequestDTO transactionRequestDTO);
    

}
