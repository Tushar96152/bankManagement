package com.Banking.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.BranchDTO;
import com.Banking.backend.dto.response.ApiResponse;

import com.Banking.backend.repository.ServiceAccessor;

@RestController
@RequestMapping("/branches")
public class BranchController {

        @GetMapping("/get-all")
    public ApiResponse<List<BranchDTO>> getAllBranches(){
        return ServiceAccessor.getBranchService().getAllBranches();
    }

}
