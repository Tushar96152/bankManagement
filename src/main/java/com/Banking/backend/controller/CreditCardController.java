package com.Banking.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking.backend.dto.request.CreditCardRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreditCardApplicationResponseDTO;
import com.Banking.backend.dto.response.CreditCardDTO;
import com.Banking.backend.repository.ServiceAccessor;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@RestController
@RequestMapping("/creditCard")
public class CreditCardController {

    @GetMapping("/get-all")
    public ApiResponse<List<CreditCardDTO>> getAllCreditCards(){
        return ServiceAccessor.getCreditCardService().getAllCreditCards();
    }

    @PostMapping("/apply")
    public ApiResponse<CreditCardApplicationResponseDTO> applyForCreditCard(@RequestBody CreditCardRequestDTO requestDTO){
        return ServiceAccessor.getCreditCardService().creditCardApplication(requestDTO);
    }
    @GetMapping("/get-by-id/{id}")
    public ApiResponse<CreditCardDTO> getCreditCardById(@PathVariable Long id){
        return ServiceAccessor.getCreditCardService().getCreditCardById(id);
    }

}
