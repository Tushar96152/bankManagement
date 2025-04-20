package com.Banking.backend.service.serviceImp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Banking.backend.dto.request.TransactionRequestDTO;
import com.Banking.backend.dto.request.TransferMoneyRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.TransactionHistoryList;
import com.Banking.backend.dto.response.TransactionResponseDTO;
import com.Banking.backend.entity.BankAccount;
import com.Banking.backend.entity.Transaction;
import com.Banking.backend.entity.TransactionType;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.TransactionService;

import ch.qos.logback.core.joran.conditional.IfAction;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
public class TransactionServiceImpl implements TransactionService {

     @Autowired private MyMailSenderImpl myMailSenderImpl;

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

        if (savAccount.getUserNetPassword() == transactionRequestDTO.getUserNetPassword()) {
            response.setCode(0);
            response.setMessage("wrong password");
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
        transaction.setBalanceAfter(newBalance);
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

        myMailSenderImpl.sendDepositNotification(savAccount, transactionRequestDTO.getAmount(), newBalance);

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

        if (savAccount.getUserNetPassword() == transactionRequestDTO.getUserNetPassword()) {
            response.setCode(0);
            response.setMessage("wrong password");
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
        transaction.setBalanceAfter(newBalance);
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

        myMailSenderImpl.sendWithdrawalNotification(savAccount, transactionRequestDTO.getAmount(), newBalance);

    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Error during withdrawal: " + e.getMessage());
    }

    return response;
}


@Override
public ApiResponse<TransactionResponseDTO> transferMoney(TransferMoneyRequestDTO transferMoneyRequestDTO) {
    ApiResponse<TransactionResponseDTO> response = new ApiResponse<>();

    try {
        // Validate and retrieve sender's bank account
        BankAccount senderAccount = RepositoryAccessor.getBankAccountRepository()
                .findByUserNetIdAndIsActive(transferMoneyRequestDTO.getUserId().trim(), true);

        if (senderAccount == null) {
            response.setCode(0);
            response.setMessage("Sender is not registered for Net Banking or account is inactive");
            return response;
        }

        // Validate and retrieve receiver's bank account
        BankAccount receiverAccount = RepositoryAccessor.getBankAccountRepository()
                .findByAccountNumber(transferMoneyRequestDTO.getReceiverAccountNumber())
                .orElse(null);

        if (receiverAccount == null || !receiverAccount.isActive()) {
            response.setCode(0);
            response.setMessage("Receiver account not found or inactive");
            return response;
        }

        // Check sufficient balance
        BigDecimal transferAmount = BigDecimal.valueOf(transferMoneyRequestDTO.getAmount());
        if (senderAccount.getBalance().compareTo(transferAmount) < 0) {
            response.setCode(0);
            response.setMessage("Insufficient balance in sender's account");
            return response;
        }

        BigDecimal senderBalaceAfter =senderAccount.getBalance().subtract(transferAmount);
        BigDecimal receiverBalaceAfter = receiverAccount.getBalance().add(transferAmount);
        senderAccount.setBalance(senderBalaceAfter);
        receiverAccount.setBalance(receiverBalaceAfter);


        // Save account changes
        RepositoryAccessor.getBankAccountRepository().save(senderAccount);
        RepositoryAccessor.getBankAccountRepository().save(receiverAccount);

        // Retrieve transaction type (TRANSFER)
        TransactionType transferType = RepositoryAccessor.getTransactionTypeRepository()
                .findById(3L)
                .orElse(null);

        if (transferType == null) {
            response.setCode(0);
            response.setMessage("Transaction type 'TRANSFER' not found");
            return response;
        }

        // Create sender transaction record
        Transaction senderTransaction = new Transaction();
        senderTransaction.setAmount(transferAmount);
        senderTransaction.setType(transferType);
        senderTransaction.setTimestamp(LocalDateTime.now());
        senderTransaction.setAccount(senderAccount);
        senderTransaction.setBalanceAfter(senderBalaceAfter);
        senderTransaction.setDescription("Transferred to account: " + receiverAccount.getAccountNumber());

        // Create receiver transaction record
        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setAmount(transferAmount);
        receiverTransaction.setType(transferType);
        receiverTransaction.setBalanceAfter(receiverBalaceAfter);
        receiverTransaction.setTimestamp(LocalDateTime.now());
        receiverTransaction.setAccount(receiverAccount);

        receiverTransaction.setDescription("Received from account: " + senderAccount.getAccountNumber());

        // Save transactions
        Transaction savedSenderTransaction = RepositoryAccessor.getTransactionRepository().save(senderTransaction);
        RepositoryAccessor.getTransactionRepository().save(receiverTransaction);

        // Send email notifications
        myMailSenderImpl.sendSenderTransferNotification(
                senderAccount.getUser().getEmail(),
                senderAccount.getUser().getName(),
                transferAmount.doubleValue(),
                receiverAccount.getAccountNumber(),
                senderAccount.getBalance().doubleValue()
        );

        myMailSenderImpl.sendReceiverTransferNotification(
                receiverAccount.getUser().getEmail(),
                receiverAccount.getUser().getName(),
                transferAmount.doubleValue(),
                senderAccount.getAccountNumber(),
                receiverAccount.getBalance().doubleValue()
        );

        // Prepare response DTO
        TransactionResponseDTO responseDTO = TransactionResponseDTO.builder()
                .transactionId(savedSenderTransaction.getId())
                .amount(transferAmount.doubleValue())
                .newBalance(senderAccount.getBalance().doubleValue())
                .timestamp(savedSenderTransaction.getTimestamp())
                .status("SUCCESS")
                .transactionType("TRANSFER")
                .build();

        response.setCode(1);
        response.setMessage("Transfer successful");
        response.setData(responseDTO);

    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Error during transfer: " + e.getMessage());
    }

    return response;
}


@Override
public ApiResponse<TransactionHistoryList> history(String userNetId) {

    ApiResponse<TransactionHistoryList> response = new ApiResponse<>();

    try {

        BankAccount savedAccount = RepositoryAccessor.getBankAccountRepository()
                .findByUserNetIdAndIsActive(userNetId, true);

                if (savedAccount == null) {
                    response.setCode(0);
                    response.setMessage("User not registered for Net Banking or account is inactive");
                    return response;
                }

                List<Transaction> transactions = RepositoryAccessor.getTransactionRepository()
                .findAllByAccountId(savedAccount.getId());

        
                List<TransactionResponseDTO> transactionDTOList = transactions.stream().map(tx -> new TransactionResponseDTO(
                    tx.getId(),
                    tx.getAmount(),
                    tx.getTimestamp(),
                    "SUCCESS", 
                    tx.getType().getName().name(),
                    tx.getDescription()
            )).collect(Collectors.toList());
    
            TransactionHistoryList data = TransactionHistoryList.builder()
                    .accountId(savedAccount.getId())
                    .transactionResponseDTOs(transactionDTOList)
                    .build();
    
            response.setCode(1);
            response.setMessage("Transaction history fetched successfully");
            response.setData(data);

        response.setCode(1);
        response.setMessage("Transaction history fetched successfully");
        response.setData(data);

        
    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("An error occurred: " + e.getMessage());
    }

    return response;
}




}
