package com.Banking.backend.service.serviceImp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Banking.backend.Enums.LoanStatus;
import com.Banking.backend.dto.request.LoanApplicationRequestDTO;
import com.Banking.backend.dto.request.LoanRepaymentRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.LoanListResponseDTO;
import com.Banking.backend.dto.response.LoanResponseDTO;
import com.Banking.backend.entity.BankAccount;
import com.Banking.backend.entity.Loan;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.LoanService;

@Service
public class LoanServiceImpl implements LoanService{

    @Autowired
    private MyMailSenderImpl myMailSenderImpl;

    @Override
    public ApiResponse<LoanResponseDTO> applyForLoan(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        ApiResponse<LoanResponseDTO> response = new ApiResponse<>();
        try {
        
            BankAccount bankAccount = RepositoryAccessor.getBankAccountRepository()
                    .findByUserNetIdAndIsActive(loanApplicationRequestDTO.getUserId(), true);

            if (bankAccount == null) {
                response.setCode(0);
                response.setMessage("User not registered or account is inactive");
                return response;
            }

            

        
            Loan newLoan = new Loan();
            newLoan.setUser(bankAccount.getUser());
            newLoan.setLoanAmount(loanApplicationRequestDTO.getLoanAmount());
            newLoan.setInterestRate(loanApplicationRequestDTO.getInterestRate());
            newLoan.setLoanType(loanApplicationRequestDTO.getLoanType());
            newLoan.setTenure(loanApplicationRequestDTO.getTenure());
            newLoan.setStatus(LoanStatus.PENDING); 
        
            Loan savedLoan = RepositoryAccessor.getLoanRepository().save(newLoan);

        
            LoanResponseDTO responseDTO = LoanResponseDTO.builder()
                    .loanId(savedLoan.getLoanId())
                    .loanAmount(savedLoan.getLoanAmount())
                    .build();

            response.setCode(1);
            response.setMessage("Loan application successful");
            response.setData(responseDTO);

        
            myMailSenderImpl.sendLoanApplicationNotification(bankAccount, loanApplicationRequestDTO.getLoanAmount());

        } catch (Exception e) {
            response.setCode(0);
            response.setMessage("Error during loan application: " + e.getMessage());
        }
        return response;
    }

@Override
public ApiResponse<List<LoanListResponseDTO>> getLoansByUserId(String userId) {
    ApiResponse<List<LoanListResponseDTO>> response = new ApiResponse<>();
    try {
        System.out.println("Get method for loan");
        
        
        BankAccount savedAccount = RepositoryAccessor.getBankAccountRepository()
                .findByUserNetIdAndIsActive(userId, true);

        
        if (savedAccount == null) {
            response.setCode(0);
            response.setMessage("User not registered or account is inactive");
            return response;
        }

        
        List<Loan> loans = RepositoryAccessor.getLoanRepository()
                .findByUser_Id(savedAccount.getUser().getId());

        
        if (loans.isEmpty()) {
            response.setCode(0);
            response.setMessage("No loans found for the given user ID");
            return response;
        }

     
        List<LoanListResponseDTO> loanListResponseDTOs = loans.stream()
                .map(loan -> LoanListResponseDTO.builder()
                        .loanId(loan.getLoanId())
                        .loanAmount(loan.getLoanAmount())
                        .createdAt(loan.getCreatedAt())
                        .status(loan.getStatus())
                        .build())
                .collect(Collectors.toList());

    
        response.setCode(1);
        response.setMessage("Loans fetched successfully");
        response.setData(loanListResponseDTOs);

    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Error fetching loans: " + e.getMessage());
    }
    return response;
}

    @Override
    public ApiResponse<LoanResponseDTO> makeRepayment(LoanRepaymentRequestDTO loanRepaymentRequestDTO) {
        ApiResponse<LoanResponseDTO> response = new ApiResponse<>();
        try {
        
            Optional<Loan> optionalLoan = RepositoryAccessor.getLoanRepository()
                    .findById(loanRepaymentRequestDTO.getLoanId());

            if (!optionalLoan.isPresent()) {
                response.setCode(0);
                response.setMessage("Loan not found");
                return response;
            }

            Loan loan = optionalLoan.get();
            BigDecimal repaymentAmount = loanRepaymentRequestDTO.getAmount();

        
            if (repaymentAmount.compareTo(loan.getLoanAmount()) > 0) {
                response.setCode(0);
                response.setMessage("Repayment amount exceeds loan balance");
                return response;
            }

          
            BigDecimal newBalance = loan.getLoanAmount().subtract(repaymentAmount);
            loan.setLoanAmount(newBalance);

          
            if (newBalance.compareTo(BigDecimal.ZERO) == 0) {
                loan.setStatus(LoanStatus.CLOSED);
            }

          
            Loan updatedLoan = RepositoryAccessor.getLoanRepository().save(loan);

          
            LoanResponseDTO responseDTO = LoanResponseDTO.builder()
                    .loanId(updatedLoan.getLoanId())
                    .loanAmount(updatedLoan.getLoanAmount())
                    .build();

            response.setCode(1);
            response.setMessage("Repayment successful");
            response.setData(responseDTO);

          
            myMailSenderImpl.sendRepaymentNotification(updatedLoan);

        } catch (Exception e) {
            response.setCode(0);
            response.setMessage("Error during loan repayment: " + e.getMessage());
        }
        return response;
    }

}
