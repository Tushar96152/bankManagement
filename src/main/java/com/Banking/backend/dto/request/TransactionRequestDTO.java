package com.Banking.backend.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class TransactionRequestDTO {

    private String userId;

    @NotNull(message = "User net password is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "User net password must be exactly 6 digits")
    private String userNetPassword;

    private BigDecimal amount;
    private String remarks; 
}
