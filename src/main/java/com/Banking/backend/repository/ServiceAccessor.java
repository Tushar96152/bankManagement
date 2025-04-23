package com.Banking.backend.repository;

import org.springframework.stereotype.Service;


import com.Banking.backend.security.CustomUserDetailsService;
import com.Banking.backend.service.BankAccountService;
import com.Banking.backend.service.BranchService;
import com.Banking.backend.service.CityService;
import com.Banking.backend.service.CreditCardService;
import com.Banking.backend.service.LoanService;
import com.Banking.backend.service.ManagerService;
import com.Banking.backend.service.TransactionService;
import com.Banking.backend.service.UserService;
import com.Banking.backend.service.serviceImp.GenericService;

import lombok.Getter;

@Service
public class ServiceAccessor {
    @Getter private static UserService userService;
    @Getter private static CustomUserDetailsService customUserDetailsService;
    @Getter private static GenericService genericService;
    @Getter private static BankAccountService bankAccountService;
    @Getter private static TransactionService transactionService;
    @Getter private static LoanService loanService;
    @Getter private static CreditCardService creditCardService;
    @Getter private static CityService cityService;
    @Getter private static BranchService branchService;
    @Getter private static ManagerService managerService;

            public ServiceAccessor(UserService userService,
            CustomUserDetailsService customUserDetailsService,
            GenericService genericService,
            BankAccountService bankAccountService,
            TransactionService transactionService,
            LoanService loanService,
            CreditCardService creditCardService,
            CityService cityService,
            BranchService branchService,
            ManagerService managerService
            ) {
        ServiceAccessor.userService = userService;
        ServiceAccessor.customUserDetailsService = customUserDetailsService;
        ServiceAccessor.genericService = genericService;
        ServiceAccessor.bankAccountService = bankAccountService;
        ServiceAccessor.transactionService = transactionService;
        ServiceAccessor.loanService = loanService;
        ServiceAccessor.creditCardService = creditCardService;
        ServiceAccessor.cityService = cityService;
        ServiceAccessor.branchService = branchService;
        ServiceAccessor.managerService = managerService;
        }
}
