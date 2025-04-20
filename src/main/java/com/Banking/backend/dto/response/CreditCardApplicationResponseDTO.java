package com.Banking.backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardApplicationResponseDTO {

    private String message;  // Success or failure message
    private Long applicationId;  // The ID of the credit card application
    private String cardType;  // The type of the card (e.g., SILVER, GOLD)
    private double annualIncome;  // The annual income of the applicant
    private String status; 
}
