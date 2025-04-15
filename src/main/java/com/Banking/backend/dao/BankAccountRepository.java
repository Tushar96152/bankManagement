package com.Banking.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.BankAccount;
import com.Banking.backend.entity.CardType;

public interface BankAccountRepository extends CrudRepository<BankAccount,Long>{

    boolean existsByAccountNumber(String number);

    boolean existsByUserNetId(String number);

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    BankAccount findByUserIdAndIsActive(Long userId, boolean b);

    BankAccount findByUserNetIdAndIsActive(String userNetId, boolean isActive);


}
