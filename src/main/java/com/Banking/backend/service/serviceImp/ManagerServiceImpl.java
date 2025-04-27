package com.Banking.backend.service.serviceImp;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Banking.backend.Enums.ApplicationStatus;
import com.Banking.backend.Enums.LoanStatus;
import com.Banking.backend.dto.request.UserRegisterRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.DashboardStatsDTO;
import com.Banking.backend.dto.response.LoanResponseDTO;
import com.Banking.backend.dto.response.UserResponse;
import com.Banking.backend.dto.response.UsersList;
import com.Banking.backend.entity.User;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

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
public ApiResponse<UserResponse> getAllUsers() {
    ApiResponse<UserResponse> response = new ApiResponse<>();
    try {
        // // Fetch all users with role 'CUSTOMER' (assuming role id = 1 for customer)
        // List<User> users = (List<User>) RepositoryAccessor.getUserRepository().findAll();


        // // Convert entities to DTOs if needed (assuming you have a UserDTO and UserResponse class)
        // List<UserResponse> userDTOs = users.stream().map(user -> UserResponse.builder()
        //         .id(user.getId())

        //         .build())
        //     .collect(Collectors.toList());

    


        // response.setCode(1);
        // response.setMessage("Successfully fetched all users");
        // response.setData(userResponse);
    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Failed to fetch users: " + e.getMessage());
    }

    return response;
}

    @Override
    public ApiResponse<UserResponse> addUser(UserRegisterRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUser'");
    }

    @Override
    public ApiResponse<LoanResponseDTO> getAllLoans() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllLoans'");
    }

}
