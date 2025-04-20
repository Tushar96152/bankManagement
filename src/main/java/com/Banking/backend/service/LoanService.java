package com.Banking.backend.service;

import java.util.List;

import com.Banking.backend.dto.request.LoanApplicationRequestDTO;
import com.Banking.backend.dto.request.LoanRepaymentRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.LoanListResponseDTO;
import com.Banking.backend.dto.response.LoanResponseDTO;
import com.Banking.backend.entity.Loan;


public interface LoanService {

    ApiResponse<LoanResponseDTO> applyForLoan(LoanApplicationRequestDTO loanApplicationRequestDTO);
    ApiResponse<List<LoanListResponseDTO>> getLoansByUserId(String userId);    
    ApiResponse<LoanResponseDTO> makeRepayment(LoanRepaymentRequestDTO loanRepaymentRequestDTO);

}
