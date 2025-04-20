package com.Banking.backend.service.serviceImp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Banking.backend.dto.BranchDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.entity.Branch;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.BranchService;


@Service
public class BranchServiceImpl  implements BranchService{

    @Override
public ApiResponse<List<BranchDTO>> getAllBranches() {
    ApiResponse<List<BranchDTO>> response = new ApiResponse<>();

    try {
        // Fetch all branches from the repository
        List<Branch> branches = (List<Branch>) RepositoryAccessor.getBranchRepository().findAll();
        
        // Map Branch entities to BranchDTO
        List<BranchDTO> branchDTOs = branches.stream()
            .map(branch -> new BranchDTO(
                branch.getId(),
                branch.getName()
            ))
            .collect(Collectors.toList());
        
        // Set the ApiResponse as success
        response.setCode(1);
        response.setMessage("Fetched all branches successfully.");
        response.setData(branchDTOs);

    } catch (Exception e) {
        // Handle exception and set the ApiResponse to failure
        response.setCode(0);
        response.setMessage("Error fetching branches: " + e.getMessage());
        response.setData(null);
        e.printStackTrace(); // Log the error for debugging
    }

    return response;
}


}
