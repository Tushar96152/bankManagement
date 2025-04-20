package com.Banking.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import com.Banking.backend.entity.Card;

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
public class CreateAccountResponse {

private Long id;
    private String accountNumber;

    // User Info
    private Long userId;
    private String userName; 

    // Branch Info
    private Long branchId;
    private String branchName; // optional

    // Account Type
    private Long accountTypeId;
    private String accountTypeName; // optional

    // Financial Info
    private BigDecimal balance;
    private boolean isActive;

    // Nominee
    private String nomineeName;
    private String nomineeRelation;

    // KYC
    private String aadhaarNumber;
    private String documentType;
    private String documentNumber;

    
    private Boolean debitCardRequired;
    private Boolean netBankingEnabled;

    
    private String cardNumbers;
    private CardResponse card;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
