package com.Banking.backend.entity;

import com.Banking.backend.Enums.ApplicationStatus;
import com.Banking.backend.Enums.CreditCardType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class CreditCardApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;  // Reference to the user who applied

    @ManyToOne
    private CreditCard creditCard;  // Reference to the credit card the user is applying for

    private double annualIncome;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;  
}
