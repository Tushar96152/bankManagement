package com.Banking.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CityDTO;
import com.Banking.backend.repository.ServiceAccessor;

@RestController
@RequestMapping("/city")
public class CityController {

    @GetMapping("/get-all")
    public ApiResponse<List<CityDTO>> getAllCities(){
        return ServiceAccessor.getCityService().getAllCities();
    }
}
