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
public class CreateAccountResponse {

    private String accountNumber;
    private String accountType;
    private LocalDateTime createdAt;
    private Double balance;
    private boolean debitCardIssued;
    private boolean checkBookIssued;
}
