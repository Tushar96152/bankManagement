package com.Banking.backend.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DebitCardGenerator {

        private static final String BANK_CODE = "1234";
    private static final Random random = new Random();

    
    public static String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        
        
        cardNumber.append(BANK_CODE);
        for (int i = 0; i < 12; i++) {
            cardNumber.append(random.nextInt(10)); 
        }
        
        
        int checkDigit = getCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    
    private static int getCheckDigit(String cardNumber) {
        int sum = 0;
        boolean shouldDouble = false;
        
        
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            
            if (shouldDouble) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            
            sum += digit;
            shouldDouble = !shouldDouble;
        }
        
        return (10 - (sum % 10)) % 10; 
    }

   
    public static String generateCVV() {
        return String.format("%03d", random.nextInt(1000)); 
    }

    
    public static String generateExpiryDate() {
        LocalDate expiryDate = LocalDate.now().plusYears(3); // Expiry date set to 3 years from today
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return expiryDate.format(formatter);
    }
}
