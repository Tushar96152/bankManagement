package com.Banking.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false,updatable = false)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "nominee_name")
    private String nomineeName;

    @Column(name = "nominee_relation")
    private String nomineeRelation;

    @Column(name = "aadhaar_number")
    private String aadhaarNumber;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "debit_card_required")
    private Boolean debitCardRequired;

    @Column(name = "net_banking_enabled")
    private Boolean netBankingEnabled ;

    private String userNetId;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType type;
    

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Card> cards;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isActive = true;

}
