package com.Banking.backend.service;

import java.util.List;

import com.Banking.backend.dto.BranchDTO;
import com.Banking.backend.dto.response.ApiResponse;


public interface BranchService {

     ApiResponse<List<BranchDTO>> getAllBranches();

}
