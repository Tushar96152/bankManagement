package com.Banking.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.Banking.backend.Enums.LoanStatus;

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
public class LoanListResponseDTO {

    private Long loanId; 
    private BigDecimal loanAmount; 
    private LoanStatus status;  
    private LocalDate disbursementDate; 
    private LocalDate createdAt;  
}
