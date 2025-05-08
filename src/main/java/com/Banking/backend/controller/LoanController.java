package com.Banking.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.request.LoanApplicationRequestDTO;
import com.Banking.backend.dto.request.LoanRepaymentRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.LoanListResponseDTO;
import com.Banking.backend.dto.response.LoanResponseDTO;

import com.Banking.backend.repository.ServiceAccessor;



@RestController
@RequestMapping("/loan")
public class LoanController {

    @PostMapping("/apply")
    public ApiResponse<LoanResponseDTO> applyLoan(@RequestBody LoanApplicationRequestDTO requestDTO){ 
        return ServiceAccessor.getLoanService().applyForLoan(requestDTO);
    }

    @PostMapping("/repay")
    public ApiResponse<LoanResponseDTO> repayLoan(@RequestBody LoanRepaymentRequestDTO requestDTO){ 
        return ServiceAccessor.getLoanService().makeRepayment(requestDTO);
    }

    @GetMapping("/get-all/{userId}")
    public ApiResponse<List<LoanListResponseDTO>> allLoans(@RequestParam("userId") String userId){ 
        return ServiceAccessor.getLoanService().getLoansByUserId(userId);
    }

}
