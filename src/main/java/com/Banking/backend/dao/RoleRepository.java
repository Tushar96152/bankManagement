package com.Banking.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.Banking.backend.entity.Role;

import com.mysql.cj.log.Log;

public interface RoleRepository  extends CrudRepository<Role,Log>{

    Optional<Role> findById(long l);

    List<Role> findByIsActive(boolean b);

}
