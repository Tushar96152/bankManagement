package com.Banking.backend.service.serviceImp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CityDTO;
import com.Banking.backend.entity.City;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.CityService;

@Service
public class CityServiceImpl implements CityService{

@Override
public ApiResponse<List<CityDTO>> getAllCities() {
    ApiResponse<List<CityDTO>> response = new ApiResponse<>();

    try {
        // Fetch all cities from the repository
        List<City> cities = (List<City>) RepositoryAccessor.getCityRepository().findAll();
        
        // Map City entities to CityDTO
        List<CityDTO> cityDTOs = cities.stream()
            .map(city -> new CityDTO(
                city.getId(),
                city.getName()
            ))
            .collect(Collectors.toList());
        
        // Set the ApiResponse as success
        response.setCode(1);
        response.setMessage("Fetched all cities successfully.");
        response.setData(cityDTOs);

    } catch (Exception e) {
        // Handle exception and set the ApiResponse to failure
        response.setCode(0);
        response.setMessage("Error fetching cities: " + e.getMessage());
        response.setData(null);
        e.printStackTrace(); // Log the error for debugging
    }

    return response;
}


}
