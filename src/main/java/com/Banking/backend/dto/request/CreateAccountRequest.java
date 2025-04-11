package com.Banking.backend.dto.request;

import com.Banking.backend.entity.City;

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

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotBlank(message = "Aadhar number is required")
    @Size(min = 12, max = 12, message = "Aadhar number must be 12 digits")
    private String aadharNo;

    @NotBlank(message = "PAN number is required")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
    private String panNo;

    private boolean wantDebitCard;
    private boolean wantCheckBook;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private City city;

    @NotNull(message = "Initial deposit is required")
    @Min(value = 500, message = "Minimum initial deposit should be ₹500")
    private Double initialDeposit;
}
