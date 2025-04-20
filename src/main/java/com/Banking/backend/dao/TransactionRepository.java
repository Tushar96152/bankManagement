package com.Banking.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction,Long>{

    List<Transaction> findAllByAccountId(Long id);

}
