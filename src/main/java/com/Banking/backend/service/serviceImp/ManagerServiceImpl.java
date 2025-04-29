package com.Banking.backend.service.serviceImp;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Banking.backend.Enums.ApplicationStatus;
import com.Banking.backend.Enums.LoanStatus;
import com.Banking.backend.dto.request.StatusChangeApplicationDTO;
import com.Banking.backend.dto.request.StatusChangeLoanDTO;
import com.Banking.backend.dto.request.UserRegisterRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreditCardApplicationResponseDTO;
import com.Banking.backend.dto.response.DashboardStatsDTO;
import com.Banking.backend.dto.response.LoanResponseDTO;
import com.Banking.backend.dto.response.UserResponse;
import com.Banking.backend.entity.BankAccount;
import com.Banking.backend.entity.CreditCardApplication;
import com.Banking.backend.entity.Loan;
import com.Banking.backend.entity.Transaction;
import com.Banking.backend.entity.TransactionType;
import com.Banking.backend.entity.User;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

    // Executor for scheduling tasks
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      @Autowired private MyMailSenderImpl myMailSenderImpl;

    @Override
    public ApiResponse<DashboardStatsDTO> getDashboardStats() {
        ApiResponse<DashboardStatsDTO> response = new ApiResponse<>();
        try {
            
            long totalCustomers = RepositoryAccessor.getUserRepository().countByRoleId(1l);
            long pandingLoans = RepositoryAccessor.getLoanRepository().countByStatus(LoanStatus.PENDING);
            long approvedLoans = RepositoryAccessor.getLoanRepository().countByStatus(LoanStatus.APPROVED);
            long pandingCreditCard = RepositoryAccessor.getCreditCardApplicationRepository().countByStatus(ApplicationStatus.PENDING);
            long approvedCreditCard = RepositoryAccessor.getCreditCardApplicationRepository().countByStatus(ApplicationStatus.APPROVED);
            long totalTransaction = RepositoryAccessor.getTransactionRepository().count();
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay(); // 2025-04-23T00:00
                LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX); // 2025-04-23T23:59:59.999999999

                long totalTransactionToday = RepositoryAccessor.getTransactionRepository()
                        .countByTimestampBetween(startOfDay, endOfDay);

            response.setCode(1);
            response.setMessage("succsssfully get detilas");

            DashboardStatsDTO dashboardStatsDTO = DashboardStatsDTO.builder()
            .totalCustomers(totalCustomers)
            .pendingLoans(pandingLoans)
            .approvedLoans(approvedLoans)
            .pendingCreditCards(pandingCreditCard)
            .approvedCreditCards(approvedCreditCard)
            .totoalTransactions(totalTransaction)
            .totalTransactionsToday(totalTransactionToday)
            .build();
            response.setData(dashboardStatsDTO);

        } catch (Exception e) {
            response.setCode(0);
            response.setMessage("INTERNAL SERVER ERROR");
        }
       return response;
    }
@Override
public ApiResponse<List<UserResponse>> getAllUsers() {
    ApiResponse<List<UserResponse>> response = new ApiResponse<>();
    try {
      
        List<User> users = (List<User>) RepositoryAccessor.getUserRepository().findAll();

        
        List<UserResponse> userResponses = users.stream()
            .map(user -> UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .dob(user.getDob())
                .roleName(user.getRole().getName().name())
                .phone(user.getPhone())
                .build())
            .collect(Collectors.toList());

        response.setCode(1);
        response.setMessage("Successfully fetched all users.");
        response.setData(userResponses);
    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Failed to fetch users: " + e.getMessage());
        response.setData(Collections.emptyList()); // Always return a valid list
    }

    return response;
}


    @Override
    public ApiResponse<UserResponse> addUser(UserRegisterRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUser'");
    }

@Override
public ApiResponse<List<LoanResponseDTO>> getAllLoans() {
    ApiResponse<List<LoanResponseDTO>> response = new ApiResponse<>();
    try {
      
        List<Loan> loans = (List<Loan>) RepositoryAccessor.getLoanRepository().findAll();

      
        List<LoanResponseDTO> loanResponses = loans.stream()
            .map(loan -> LoanResponseDTO.builder()
                .loanId(loan.getLoanId())
                .applicationDate(loan.getCreatedAt())
                .loanAmount(loan.getLoanAmount())
                .loanStatus(loan.getStatus().name())
                .build())
            .collect(Collectors.toList());

        response.setCode(1);
        response.setMessage("Successfully fetched all loans.");
        response.setData(loanResponses);
    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Failed to fetch loans: " + e.getMessage());
        response.setData(Collections.emptyList());
    }
    return response;
}
@Override
public ApiResponse<List<CreditCardApplicationResponseDTO>> getAllCreditCardApplications() {
    ApiResponse<List<CreditCardApplicationResponseDTO>> response = new ApiResponse<>();
    try {
        
        List<CreditCardApplication> applications = (List<CreditCardApplication>) RepositoryAccessor.getCreditCardApplicationRepository().findAll();

       
        List<CreditCardApplicationResponseDTO> applicationDTOs = applications.stream()
            .map(app -> CreditCardApplicationResponseDTO.builder()
                .message("Success")
                .annualIncome(app.getAnnualIncome())
                .applicationId(app.getId())
                .cardType(app.getCreditCard().getType().name())
                .status(app.getStatus())
                .build())
            .collect(Collectors.toList());

        response.setCode(1);
        response.setMessage("Successfully fetched all credit card applications.");
        response.setData(applicationDTOs);
    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Failed to fetch credit card applications: " + e.getMessage());
        response.setData(Collections.emptyList());
    }
    return response;
}
@Override
public ApiResponse<?> statusChangeLoan(StatusChangeLoanDTO request) {
   ApiResponse<?> response = new ApiResponse<>();

   try {
    
      Loan loan = RepositoryAccessor.getLoanRepository().findById(request.getId()).orElse(null); 

     
    System.out.println(request.getId());

      if (loan == null) {
        response.setCode(0);
        response.setMessage("Application Not Found");
        return response;
      }

      BankAccount savedAccount = RepositoryAccessor.getBankAccountRepository().findByUserIdAndIsActive( loan.getUser().getId(), true);
      if (request.getStatus() == LoanStatus.APPROVED) {
        loan.setApprovedDate(LocalDate.now());
        scheduleLoanDisbursement(request.getId(), 15);
        myMailSenderImpl.sendLoanApprovalNotification(savedAccount, loan.getLoanAmount());
        
    }
      loan.setStatus(request.getStatus());
      RepositoryAccessor.getLoanRepository().save(loan);

      response.setCode(1);
      response.setMessage("Status Changeed");


   } catch (Exception e) {
        response.setCode(0);
        e.printStackTrace();
        response.setMessage("Internal Server Error ");
   }
   return response;
}
@Override
public ApiResponse<?> statusChangeApp(StatusChangeApplicationDTO request) {
    ApiResponse<?> response = new ApiResponse<>();

    try {
        CreditCardApplication application = RepositoryAccessor.getCreditCardApplicationRepository()
                .findById(request.getId())
                .orElse(null);

        if (application == null) {
            response.setCode(0);
            response.setMessage("Application Not Found");
            return response;
        }

        application.setStatus(request.getStatus());
        RepositoryAccessor.getCreditCardApplicationRepository().save(application);

        response.setCode(1);
        response.setMessage("Status Changed Successfully");

    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Internal Server Error: " + e.getMessage());
    }

    return response;
}

private void scheduleLoanDisbursement(Long loanId, long delayInMinutes) {
        // Runnable task to disburse the loan after a delay
        Runnable disburseTask = new Runnable() {
            @Override
            public void run() {
                // Disburse loan after 15 minutes
                disburseLoan(loanId);
            }
        };

        scheduler.schedule(disburseTask, delayInMinutes, TimeUnit.MINUTES);
    }

    private void disburseLoan(Long loanId) {
        Loan loan = RepositoryAccessor.getLoanRepository().findById(loanId).orElse(null);

        if (loan != null && loan.getStatus() == LoanStatus.APPROVED) {
            loan.setStatus(LoanStatus.DISBURSED);
            loan.setDisbursementDate(LocalDateTime.now()); 

            BigDecimal loanAmount = loan.getLoanAmount();
            Long userId = loan.getUser().getId();

            BankAccount savAccount = RepositoryAccessor.getBankAccountRepository().findByUserIdAndIsActive(userId, true);
            BigDecimal newAmmount = savAccount.getBalance().add(loanAmount);
            savAccount.setBalance(newAmmount);

                TransactionType transactionType = RepositoryAccessor.getTransactionTypeRepository()
                .findById(9L).orElse(null);

                Transaction transaction = new Transaction();
                transaction.setAmount(loanAmount);
                transaction.setType(transactionType);
                transaction.setBalanceAfter(newAmmount);
                transaction.setTimestamp(LocalDateTime.now());
                transaction.setAccount(savAccount);
                transaction.setDescription("");
                Transaction savedTransaction = RepositoryAccessor.getTransactionRepository().save(transaction);


            RepositoryAccessor.getLoanRepository().save(loan);

            myMailSenderImpl.sendLoanDisbursementNotification(savAccount, loanAmount, newAmmount, savedTransaction.getId());

            System.out.println("Loan with ID " + loanId + " has been successfully disbursed.");
        }
    }
}
