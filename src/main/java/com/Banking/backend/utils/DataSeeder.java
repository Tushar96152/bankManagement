package com.Banking.backend.utils;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Banking.backend.repository.ServiceAccessor;


@Component
public class DataSeeder implements CommandLineRunner {


    public void run(String... args) throws Exception {
        ServiceAccessor.getGenericService().seedRoles();
        ServiceAccessor.getGenericService().seedAccountTypes();;
        ServiceAccessor.getGenericService().seedTransactionTypes();
        ServiceAccessor.getGenericService().seedCardTypes();

    }
}
