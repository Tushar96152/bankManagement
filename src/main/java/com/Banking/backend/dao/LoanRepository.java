package com.Banking.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.Banking.backend.entity.Loan;

public interface LoanRepository  extends CrudRepository<Loan,Long>{

   
    List<Loan> findByUser_Id(Long userId);

    // long countByStatus(String string);



}
