package com.Banking.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.request.TransactionRequestDTO;
import com.Banking.backend.dto.request.TransferMoneyRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.TransactionHistoryList;
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
    @PostMapping("/transfer")
    public ApiResponse<TransactionResponseDTO> transferMoney(@RequestBody TransferMoneyRequestDTO requestDTO){
        return ServiceAccessor.getTransactionService().transferMoney(requestDTO);
    }
    @GetMapping("/history/{userId}")
    public ApiResponse<TransactionHistoryList> getHistory(@RequestParam("userId") String userNetId){
        return ServiceAccessor.getTransactionService().history(userNetId);
    }

}
