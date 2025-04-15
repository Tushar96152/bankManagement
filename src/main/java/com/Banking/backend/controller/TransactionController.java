package com.Banking.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.request.TransactionRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.TransactionResponseDTO;
import com.Banking.backend.repository.ServiceAccessor;



@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @PostMapping("/deposite")
    public ApiResponse<TransactionResponseDTO> depositeMoney(@RequestBody TransactionRequestDTO requestDTO){
        System.out.println("User ID from request DTO = " + requestDTO.getUserId());
        return ServiceAccessor.getTransactionService().depositeMoney(requestDTO);
    }
    @PostMapping("/withdraw")
    public ApiResponse<TransactionResponseDTO> withDrawMoney(@RequestBody TransactionRequestDTO requestDTO){
        return ServiceAccessor.getTransactionService().withDrawMoney(requestDTO);
    }

}
