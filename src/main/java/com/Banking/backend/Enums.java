package com.Banking.backend;

public class Enums {

    public enum RoleName{
        MANAGER,
        CUSTOMER,
        AGENT
    }
    public enum AccountTypeName{
        SAVING,
        CURRENT,
        SALARY
    }
    public enum CardTypeName {
        DEBIT_CARD("Debit Card"),
        CREDIT_CARD("Credit Card");
    
        private final String label;
    
        CardTypeName(String label) {
            this.label = label;
        }
    
        public String getLabel() {
            return label;
        }
    }
    
    public enum TransactionTypeName {

        DEPOSIT("Deposit"),
        WITHDRAWAL("Withdrawal"),
        TRANSFER("Transfer"),
        BILL_PAYMENT("Bill Payment"),
        MOBILE_RECHARGE("Mobile Recharge"),
        EMI_PAYMENT("EMI Payment"),
        INTEREST_CREDIT("Interest Credit"),
        FUND_RECEIVED("Fund Received"),
        FUND_SENT("Fund Sent");
    
        private final String label;
    
        TransactionTypeName(String label) {
            this.label = label;
        }
    
        public String getLabel() {
            return label;
        }
    }
    
    public enum LoanStatus {
        PENDING,
        APPROVED,
        DISBURSED,
        CLOSED;
    }


}
