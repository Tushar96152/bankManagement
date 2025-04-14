package com.Banking.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.CardType;
import com.Banking.backend.entity.User;

public interface CardTypeRepository  extends CrudRepository<CardType,Long>{

    List<CardType> findByActive(boolean b);

    Optional<CardType> findByName(String string);

    Optional<CardType> findById(Long id);

}
