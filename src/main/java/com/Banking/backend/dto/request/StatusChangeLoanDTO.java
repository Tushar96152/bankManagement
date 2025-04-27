package com.Banking.backend.dto.request;

import com.Banking.backend.Enums.ApplicationStatus;
import com.Banking.backend.Enums.LoanStatus;

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
public class StatusChangeLoanDTO {

    private Long id;
    private LoanStatus status;

}
