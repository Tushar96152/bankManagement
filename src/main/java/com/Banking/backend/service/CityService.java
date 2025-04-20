package com.Banking.backend.service;

import java.util.List;

import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CityDTO;

public interface CityService {


    ApiResponse<List<CityDTO>> getAllCities();

}
