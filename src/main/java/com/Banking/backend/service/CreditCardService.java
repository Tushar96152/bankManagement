package com.Banking.backend.service;

import java.util.List;

import com.Banking.backend.dto.request.CreditCardRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreditCardApplicationResponseDTO;
import com.Banking.backend.dto.response.CreditCardDTO;

public interface CreditCardService {

    ApiResponse<List<CreditCardDTO>> getAllCreditCards();
    ApiResponse<CreditCardApplicationResponseDTO> creditCardApplication(CreditCardRequestDTO requestDTO);

}
