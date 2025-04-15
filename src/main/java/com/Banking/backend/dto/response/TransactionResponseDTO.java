package com.Banking.backend.dto.response;

import java.time.LocalDateTime;

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
public class TransactionResponseDTO {

    private Long transactionId;
    private Double amount;
    private Double newBalance;
    private LocalDateTime timestamp;
    private String status; 
    private String transactionType; 
}
