package com.Banking.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.TransactionType;

public interface TransactionTypeRepository  extends CrudRepository<TransactionType,Long>{

    List<TransactionType> findByActive(boolean b);

}
