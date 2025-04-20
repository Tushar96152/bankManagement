package com.Banking.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRepayment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repaymentId;

    private BigDecimal paymentAmount;
    private LocalDate paymentDate;
    private BigDecimal remainingBalance;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;  

    private LocalDate createdAt;
}
