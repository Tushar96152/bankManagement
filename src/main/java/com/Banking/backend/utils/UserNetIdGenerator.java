package com.Banking.backend.utils;

import java.util.Random;

public class UserNetIdGenerator {

    private static final String PREFIX = "R"; 

        public static String generateUserNetId() {
       
        String randomDigits = String.format("%09d", new Random().nextInt(1000000000));

        return PREFIX + randomDigits; 
    }
}
