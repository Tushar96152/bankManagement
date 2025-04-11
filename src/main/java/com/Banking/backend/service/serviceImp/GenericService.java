package com.Banking.backend.service.serviceImp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Banking.backend.Enums.AccountTypeName;
import com.Banking.backend.Enums.CardTypeName;
import com.Banking.backend.Enums.RoleName;
import com.Banking.backend.Enums.TransactionTypeName;
import com.Banking.backend.entity.AccountType;
import com.Banking.backend.entity.CardType;
import com.Banking.backend.entity.Role;
import com.Banking.backend.entity.TransactionType;
import com.Banking.backend.repository.RepositoryAccessor;

@Service
public class GenericService {


@Transactional
public void seedRoles() {
    List<RoleName> roleNames = Arrays.asList(RoleName.values());

    List<Role> existingRoles = RepositoryAccessor.getRoleRepository().findByIsActive(true);
    List<RoleName> existingRoleNames = existingRoles.stream()
            .map(Role::getName)
            .collect(Collectors.toList());

    List<Role> newRoles = roleNames.stream()
            .filter(name -> !existingRoleNames.contains(name)) // Only insert if not already in DB
            .map(name -> {
                Role role = new Role();
                role.setName(name);
                role.setActive(true);
                return role;
            })
            .collect(Collectors.toList());

    if (!newRoles.isEmpty()) {
        RepositoryAccessor.getRoleRepository().saveAll(newRoles);
    }
}
@Transactional
public void seedAccountTypes() {
    List<AccountTypeName> accountTypeNames = Arrays.asList(AccountTypeName.values());

    List<AccountType> existingTypes = RepositoryAccessor.getAccountTypeRepository().findByActive(true);
    List<AccountTypeName> existingTypeNames = existingTypes.stream()
            .map(AccountType::getName)
            .collect(Collectors.toList());

    List<AccountType> newTypes = accountTypeNames.stream()
            .filter(name -> !existingTypeNames.contains(name)) // Insert only if not already in DB
            .map(name -> {
                AccountType type = new AccountType();
                type.setName(name);
                type.setActive(true);
                return type;
            })
            .collect(Collectors.toList());

    if (!newTypes.isEmpty()) {
        RepositoryAccessor.getAccountTypeRepository().saveAll(newTypes);
    }
}

@Transactional
public void seedCardTypes() {
    List<CardTypeName> cardTypeNames = Arrays.asList(CardTypeName.values());

    List<CardType> existingTypes = RepositoryAccessor.getCardTypeRepository().findByActive(true);
    List<CardTypeName> existingTypeNames = existingTypes.stream()
            .map(CardType::getName)
            .collect(Collectors.toList());

    List<CardType> newTypes = cardTypeNames.stream()
            .filter(name -> !existingTypeNames.contains(name)) // Insert only if not already in DB
            .map(name -> {
                CardType type = new CardType();
                type.setName(name);
                type.setActive(true);
                return type;
            })
            .collect(Collectors.toList());

    if (!newTypes.isEmpty()) {
        RepositoryAccessor.getCardTypeRepository().saveAll(newTypes);
    }
}

@Transactional
public void seedTransactionTypes() {
    List<TransactionTypeName> transactionTypeNames = Arrays.asList(TransactionTypeName.values());

    List<TransactionType> existingTypes = RepositoryAccessor.getTransactionTypeRepository().findByActive(true);
    List<TransactionTypeName> existingTypeNames = existingTypes.stream()
            .map(TransactionType::getName)
            .collect(Collectors.toList());

    List<TransactionType> newTypes = transactionTypeNames.stream()
            .filter(name -> !existingTypeNames.contains(name)) // Insert only if not already in DB
            .map(name -> {
                TransactionType type = new TransactionType();
                type.setName(name);
                type.setActive(true);
                return type;
            })
            .collect(Collectors.toList());

    if (!newTypes.isEmpty()) {
        RepositoryAccessor.getTransactionTypeRepository().saveAll(newTypes);
    }
}


}
