package com.Banking.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class LoanResponseDTO {

     private Long loanId;
    private String userId;
    private BigDecimal loanAmount;
    private BigDecimal outstandingAmount;
    private String loanStatus; // Example: "APPROVED", "PENDING", etc.
    private LocalDate applicationDate;
    private LocalDate disbursementDate;
    private LocalDate dueDate;
}
