package com.Banking.backend.service.serviceImp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import org.springframework.stereotype.Service;

import com.Banking.backend.dto.request.TransactionRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.TransactionResponseDTO;
import com.Banking.backend.entity.BankAccount;
import com.Banking.backend.entity.Transaction;
import com.Banking.backend.entity.TransactionType;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.TransactionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class TransactionServiceImpl implements TransactionService {

@Override
public ApiResponse<TransactionResponseDTO> depositeMoney(@RequestBody TransactionRequestDTO transactionRequestDTO) {

    ApiResponse<TransactionResponseDTO> response = new ApiResponse<>();

    try {
        
        BankAccount savAccount = RepositoryAccessor.getBankAccountRepository()
                .findByUserNetIdAndIsActive(transactionRequestDTO.getUserId().trim(), true);

       
        if (savAccount == null) {
            response.setCode(0);
            response.setMessage("User not registered for Net Banking");
            return response;
        }

        
        BigDecimal currentBalance = savAccount.getBalance();
        BigDecimal newBalance = currentBalance.add(transactionRequestDTO.getAmount());
        savAccount.setBalance(newBalance);
        RepositoryAccessor.getBankAccountRepository().save(savAccount);

        
        TransactionType depositType = RepositoryAccessor.getTransactionTypeRepository()
                .findById(1l).orElse(null);
        if (depositType == null) {
            response.setCode(0);
            response.setMessage("Transaction type 'DEPOSIT' not found");
            return response;
        }

       
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setType(depositType);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(savAccount);
        transaction.setDescription(transactionRequestDTO.getRemarks());

        Transaction savedTransaction = RepositoryAccessor.getTransactionRepository().save(transaction);


        TransactionResponseDTO responseDTO = TransactionResponseDTO.builder()
        .transactionId(savedTransaction.getId())
        .amount(transactionRequestDTO.getAmount().doubleValue())
        .newBalance(newBalance.doubleValue())
        .timestamp(savedTransaction.getTimestamp())
        .status("SUCCESS")
        .transactionType("DEPOSIT")
        .build();
        
        response.setCode(1);
        response.setMessage("Deposit successful");
        response.setData(responseDTO);

    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Error during deposit: " + e.getMessage());
    }

    return response;
}


@Override
public ApiResponse<TransactionResponseDTO> withDrawMoney(@RequestBody TransactionRequestDTO transactionRequestDTO) {

    ApiResponse<TransactionResponseDTO> response = new ApiResponse<>();

    try {
       
        BankAccount savAccount = RepositoryAccessor.getBankAccountRepository()
                .findByUserNetIdAndIsActive(transactionRequestDTO.getUserId(), true);

        if (savAccount == null) {
            response.setCode(0);
            response.setMessage("User not registered for Net Banking");
            return response;
        }

        
        BigDecimal currentBalance = savAccount.getBalance();
        BigDecimal withdrawalAmount = transactionRequestDTO.getAmount();

        if (currentBalance.compareTo(withdrawalAmount) < 0) {
            response.setCode(0);
            response.setMessage("Insufficient balance");
            return response;
        }

        
        BigDecimal newBalance = currentBalance.subtract(withdrawalAmount);
        savAccount.setBalance(newBalance);
        RepositoryAccessor.getBankAccountRepository().save(savAccount);

        
        TransactionType withdrawType = RepositoryAccessor.getTransactionTypeRepository()
                .findById(2L).orElse(null); 
        if (withdrawType == null) {
            response.setCode(0);
            response.setMessage("Transaction type 'WITHDRAW' not found");
            return response;
        }

        
        Transaction transaction = new Transaction();
        transaction.setAmount(withdrawalAmount);
        transaction.setType(withdrawType);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(savAccount);
        transaction.setDescription(transactionRequestDTO.getRemarks());

        Transaction savedTransaction = RepositoryAccessor.getTransactionRepository().save(transaction);

       
        TransactionResponseDTO responseDTO = TransactionResponseDTO.builder()
                .transactionId(savedTransaction.getId())
                .amount(withdrawalAmount.doubleValue())
                .newBalance(newBalance.doubleValue())
                .timestamp(savedTransaction.getTimestamp())
                .status("SUCCESS")
                .transactionType("WITHDRAW")
                .build();

        
        response.setCode(1);
        response.setMessage("Withdrawal successful");
        response.setData(responseDTO);

    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Error during withdrawal: " + e.getMessage());
    }

    return response;
}


}
