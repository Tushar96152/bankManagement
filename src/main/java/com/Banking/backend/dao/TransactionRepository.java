package com.Banking.backend.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction,Long>{

    List<Transaction> findAllByAccountId(Long id);

    long countByTimestampBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

}
