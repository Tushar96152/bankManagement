package com.Banking.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.Card;

public interface CardRepository extends CrudRepository<Card,Long> {

    boolean existsByCardNumber(String cardNumber);

}
