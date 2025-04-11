package com.Banking.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount,Long>{

}
