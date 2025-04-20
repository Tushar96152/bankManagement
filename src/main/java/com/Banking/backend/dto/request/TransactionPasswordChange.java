package com.Banking.backend.dto.request;

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
public class TransactionPasswordChange {

    private String userId;
    @Pattern(regexp = "^\\d{6}$", message = "Password must be exactly 6 digits")
    private String newPassword;

}
