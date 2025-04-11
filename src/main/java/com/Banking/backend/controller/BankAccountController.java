package com.Banking.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class BankAccountController {


    @PostMapping("/create")
    public ResponseEntity<?> createAccount()
    {
        return ResponseEntity.ok().body("done");
    }
}
