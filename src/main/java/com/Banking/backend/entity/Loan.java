package com.Banking.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.Banking.backend.Enums.LoanStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private String loanType;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private Integer tenure;  

     @Enumerated(EnumType.STRING)  // This is where you use the Enum
    private LoanStatus status;  // Status of the loan (Pending, Approved, Disbursed, Closed)
    private BigDecimal approvedAmount;
    private Integer approvedTenure;
    private LocalDateTime disbursementDate;  // already present ✅
    private LocalDate approvedDate;   // Date when the loan is disbursed

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Foreign key to User

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanRepayment> repayments;  // A loan can have multiple repayments

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

}
