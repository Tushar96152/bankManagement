package com.Banking.backend.dto.response;

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
public class DashboardStatsDTO {

    private long totalCustomers;
    private long pendingLoans;
    private long approvedLoans;
    private long pendingCreditCards;
    private long approvedCreditCards;
    private long totalTransactionsToday;
}
