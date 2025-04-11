package com.Banking.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.User;

public interface UserRepository extends CrudRepository<User,Long>{

    Optional<User> findByEmailIgnoreCase(String email);



}
