package com.Banking.backend.repository;

import org.springframework.stereotype.Service;
import com.Banking.backend.security.CustomUserDetailsService;
import com.Banking.backend.service.UserService;
import com.Banking.backend.service.serviceImp.GenericService;

import lombok.Getter;

@Service
public class ServiceAccessor {
    @Getter private static UserService userService;
    @Getter private static CustomUserDetailsService customUserDetailsService;
    @Getter private static GenericService genericService;

            public ServiceAccessor(UserService userService,
            CustomUserDetailsService customUserDetailsService,
            GenericService genericService) {
        ServiceAccessor.userService = userService;
        ServiceAccessor.customUserDetailsService = customUserDetailsService;
        ServiceAccessor.genericService = genericService;
        }
}
