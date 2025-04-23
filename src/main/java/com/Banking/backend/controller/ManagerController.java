package com.Banking.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.DashboardStatsDTO;
import com.Banking.backend.repository.ServiceAccessor;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @GetMapping("/dashboard")
    public ApiResponse<DashboardStatsDTO> getDashbordStatus(){
        return ServiceAccessor.getManagerService().getDashboardStats();
    }

}
