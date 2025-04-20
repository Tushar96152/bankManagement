package com.Banking.backend.dto.request;

import java.math.BigDecimal;

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
public class LoanApplicationRequestDTO {

    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private Integer tenure;  // in months
    private String loanType; // e.g., Personal, Home, Auto
    private String userId;
}
