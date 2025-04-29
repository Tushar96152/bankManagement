package com.Banking.backend.utils;

import java.util.Random;

public class OTPUtil {

        public static String generateSixDigitOTP() {
        int otp = 100000 + new Random().nextInt(900000); 
        return String.valueOf(otp);
    }
}
