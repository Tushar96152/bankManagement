package com.Banking.backend.dto.request;

import java.math.BigDecimal;

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
public class LoanRepaymentRequestDTO {

    private Long loanId;
    private String userId;
    private BigDecimal amount;
    private String remark;
}
