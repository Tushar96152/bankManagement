package com.Banking.backend.service;

import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.DashboardStatsDTO;

public interface ManagerService {

     ApiResponse<DashboardStatsDTO> getDashboardStats();

}
