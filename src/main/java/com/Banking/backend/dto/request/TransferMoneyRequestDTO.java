package com.Banking.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class TransferMoneyRequestDTO {

    private String userId;

    @NotNull(message = "Receiver account number is required")
    @Size(min = 10, max = 20, message = "Receiver account number must be between 10 and 20 characters")
    private String receiverAccountNumber;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be a positive value")
    private Double amount;

    @Size(max = 255, message = "Transaction description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "User net password is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "User net password must be exactly 6 digits")
    private String userNetPassword;
}
