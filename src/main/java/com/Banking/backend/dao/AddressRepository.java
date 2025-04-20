package com.Banking.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.Address;

public interface AddressRepository  extends CrudRepository<Address,Long>{

}
