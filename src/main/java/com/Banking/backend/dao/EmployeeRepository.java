package com.Banking.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee,Long>{

}
