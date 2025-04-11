package com.Banking.backend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.CardType;

public interface CardTypeRepository  extends CrudRepository<CardType,Long>{

    List<CardType> findByActive(boolean b);

}
