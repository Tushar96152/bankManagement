package com.Banking.backend.dto.response;

import com.Banking.backend.Enums.CreditCardType;

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
public class CreditCardDTO {

    private Long id;
    private CreditCardType type;
    private String name;
    private String description;
    private Double annualFee;
    private Double interestRate;
    private Double creditLimit;

}
