package com.Banking.backend.repository;

import org.springframework.stereotype.Service;

import com.Banking.backend.dao.AccountTypeRepository;
import com.Banking.backend.dao.BankAccountRepository;
import com.Banking.backend.dao.BranchRepository;
import com.Banking.backend.dao.CardRepository;
import com.Banking.backend.dao.CardTypeRepository;
import com.Banking.backend.dao.CityRepository;
import com.Banking.backend.dao.EmployeeRepository;
import com.Banking.backend.dao.RoleRepository;
import com.Banking.backend.dao.TransactionRepository;
import com.Banking.backend.dao.TransactionTypeRepository;
import com.Banking.backend.dao.UserRepository;


import lombok.Getter;

@Service
public class RepositoryAccessor {
    @Getter private static UserRepository userRepository;
    @Getter private static RoleRepository roleRepository;
    @Getter private static BankAccountRepository bankAccountRepository;
    @Getter private static EmployeeRepository employeeRepository;
    @Getter private static CardRepository cardRepository;
    @Getter private static BranchRepository branchRepository;
    @Getter private static CityRepository cityRepository;
    @Getter private static TransactionRepository transactionRepository;
    @Getter private static AccountTypeRepository accountTypeRepository;
    @Getter private static CardTypeRepository cardTypeRepository;
    @Getter private static TransactionTypeRepository transactionTypeRepository;

    public RepositoryAccessor(
        UserRepository userRepository,
        RoleRepository roleRepository,
        BankAccountRepository bankAccountRepository,
        EmployeeRepository employeeRepository,
        CardRepository cardRepository,
        BranchRepository branchRepository,
        CityRepository cityRepository,
        TransactionRepository transactionRepository,
        AccountTypeRepository accountTypeRepository,
        CardTypeRepository cardTypeRepository,
        TransactionTypeRepository transactionTypeRepository
    ) {
        RepositoryAccessor.userRepository = userRepository;
        RepositoryAccessor.roleRepository = roleRepository;
        RepositoryAccessor.bankAccountRepository = bankAccountRepository;
        RepositoryAccessor.employeeRepository = employeeRepository;
        RepositoryAccessor.cardRepository = cardRepository;
        RepositoryAccessor.branchRepository = branchRepository;
        RepositoryAccessor.cityRepository = cityRepository;
        RepositoryAccessor.transactionRepository = transactionRepository;
        RepositoryAccessor.accountTypeRepository = accountTypeRepository;
        RepositoryAccessor.cardTypeRepository = cardTypeRepository;
        RepositoryAccessor.transactionTypeRepository = transactionTypeRepository;
    }

}
