package com.Banking.backend.dto.response;

import com.Banking.backend.entity.CardType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponse {

     private String cardNumber;

    private String type;
    
    
    private String expiryDate;

    
    private String cvv;

    private boolean status;
}
