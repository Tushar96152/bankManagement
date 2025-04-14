package com.Banking.backend.utils;

import java.util.Random;

public class AccountNumberGenerator {
        private static final String BANK_CODE = "5200";
    private static final String BRANCH_CODE = "1000"; 

    
    public static String generate() {
        
        String randomDigits = String.format("%04d", new Random().nextInt(10000));

        return BANK_CODE + "-" + BRANCH_CODE + "-" + randomDigits;
    }
}
