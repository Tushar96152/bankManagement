package com.Banking.backend.dto.response;

import java.math.BigDecimal;
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
    private String description;

    public TransactionResponseDTO(Long transactionId, BigDecimal amount, LocalDateTime timestamp, String status, String transactionType,String description) {
        this.transactionId = transactionId;
        this.amount = amount.doubleValue();
        this.timestamp = timestamp;
        this.status = status;
        this.transactionType = transactionType;
        this.description = description;
    }
}
