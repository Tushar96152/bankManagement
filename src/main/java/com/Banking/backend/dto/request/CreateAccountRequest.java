package com.Banking.backend.dto.request;

import java.math.BigDecimal;

import com.Banking.backend.entity.City;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class CreateAccountRequest {
 @NotNull(message = "User ID is required")
    private Long userId;

    
    @NotNull(message = "Branch ID is required")
    private Long branchId;

    
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be non-negative")
    private BigDecimal balance;

   
    private String nomineeName;
    private String nomineeRelation;

    
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must be 12 digits")
    private String aadhaarNumber;

    private String documentType;
    private String documentNumber;

    
    @NotNull(message = "Account type ID is required")
    private Long accountTypeId;

    
    private boolean debitCardRequired ;
    
    private boolean netBankingEnabled ;
}
