package com.Banking.backend.service.serviceImp;

import org.springframework.stereotype.Service;

import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.DashboardStatsDTO;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

    // @Override
    // public ApiResponse<DashboardStatsDTO> getDashboardStats() {
    //     ApiResponse<DashboardStatsDTO> response = new ApiResponse<>();
    //     try {
            
    //         long totalCustomers = RepositoryAccessor.getUserRepository().countByRole(1l);
    //         long pandingLoans = RepositoryAccessor.getLoanRepository().countByStatus("PANDING");


    //     } catch (Exception e) {
    //         // TODO: handle exception
    //     }
    //    return response;
    // }

}
