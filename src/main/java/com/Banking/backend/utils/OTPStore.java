package com.Banking.backend.utils;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OTPStore {
    private static final Map<String, OTPEntry> otpMap = new ConcurrentHashMap<>();
    private static final long OTP_EXPIRY_MINUTES = 10;

    public static String generateAndStoreOTP(String userEmail) {
        String otp = String.valueOf(100000 + new SecureRandom().nextInt(900000)); // 6-digit OTP
        otpMap.put(userEmail, new OTPEntry(otp, LocalDateTime.now()));
        return otp;
    }

    public static boolean validateOTP(String userEmail, String enteredOtp) {
        OTPEntry stored = otpMap.get(userEmail);
        if (stored == null) return false;

        boolean isExpired = Duration.between(stored.generatedTime, LocalDateTime.now())
                .toMinutes() >= OTP_EXPIRY_MINUTES;

        if (isExpired || !stored.otp.equals(enteredOtp)) {
            otpMap.remove(userEmail);
            return false;
        }

        otpMap.remove(userEmail); 
        return true;
    }

    private static class OTPEntry {
        String otp;
        LocalDateTime generatedTime;

        OTPEntry(String otp, LocalDateTime generatedTime) {
            this.otp = otp;
            this.generatedTime = generatedTime;
        }
    }
}
