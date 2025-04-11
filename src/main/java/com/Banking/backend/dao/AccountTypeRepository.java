package com.Banking.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.AccountType;

public interface AccountTypeRepository extends CrudRepository<AccountType,Long> {

    List<AccountType> findByActive(boolean b);


}
