package com.Banking.backend.dao;


import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.LoanRepayment;

public interface LoanRepaymentRepository extends CrudRepository<LoanRepayment,Long> {

}
