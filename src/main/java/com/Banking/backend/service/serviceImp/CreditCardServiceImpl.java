package com.Banking.backend.service.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.Banking.backend.Enums.ApplicationStatus;

import com.Banking.backend.dto.request.CreditCardRequestDTO;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreditCardApplicationResponseDTO;
import com.Banking.backend.dto.response.CreditCardDTO;
import com.Banking.backend.entity.BankAccount;
import com.Banking.backend.entity.CreditCard;
import com.Banking.backend.entity.CreditCardApplication;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.CreditCardService;

@Service
public class CreditCardServiceImpl implements CreditCardService{

    @Override
    public ApiResponse<List<CreditCardDTO>> getAllCreditCards() {
        ApiResponse<List<CreditCardDTO>> response = new ApiResponse<>();
    
        try {
            Iterable<CreditCard> iterable = RepositoryAccessor.getCreditCardRepository().findAll();
            List<CreditCard> cards = StreamSupport
                    .stream(iterable.spliterator(), false)
                    .collect(Collectors.toList());

    
            List<CreditCardDTO> creditCardDTOs = cards.stream()
                    .map(card -> new CreditCardDTO(
                            card.getId(),
                            card.getType(),
                            card.getName(),
                            card.getDescription(),
                            card.getAnnualFee(),
                            card.getInterestRate(),
                            card.getCreditLimit()
                    )).collect(Collectors.toList());
    
            response.setData(creditCardDTOs);
            response.setMessage("Credit cards fetched successfully");
            response.setData(creditCardDTOs);
        } catch (Exception e) {
            response.setCode(0);
            response.setMessage("Failed to fetch credit cards: " + e.getMessage());
            
        }
    
        return response;
    }

@Override
public ApiResponse<CreditCardApplicationResponseDTO> creditCardApplication(CreditCardRequestDTO requestDTO) {
    ApiResponse<CreditCardApplicationResponseDTO> response = new ApiResponse<>();
    
    try {
// Step 1: Validate user existence
BankAccount bankAccount = RepositoryAccessor.getBankAccountRepository()
        .findByUserNetIdAndIsActive(requestDTO.getUserId(), true);

if (bankAccount == null) {
    response.setCode(0);
    response.setMessage("User does not have an active bank account. Please create an account first.");
    return response;
}


        

        // Step 2: Validate credit card type (you may have a list of available credit cards)
        Optional<CreditCard> savedCreditCard = RepositoryAccessor.getCreditCardRepository().findById(requestDTO.getCreditCardId());
        
       CreditCardApplication application = new CreditCardApplication();
       application.setCreditCard(savedCreditCard.get());
       application.setAnnualIncome(requestDTO.getAnnualIncome());
       application.setStatus(ApplicationStatus.PENDING);

        // Step 4: Save the application to the database
        application = RepositoryAccessor.getCreditCardApplicationRepository().save(application);

        CreditCardApplicationResponseDTO responseDTO = CreditCardApplicationResponseDTO.builder()
        .applicationId(application.getId())
        .cardType(application.getCreditCard().getType().name())
        .annualIncome(application.getAnnualIncome())
        .status(application.getStatus().name())
        .build();


        // Set successful response
        response.setCode(0);
        response.setMessage("Credit card application submitted successfully.");
        response.setData(responseDTO);

    } catch (Exception e) {
        // Handle exceptions and provide an appropriate error message
        response.setCode(0);
        response.setMessage("Failed to submit credit card application: " + e.getMessage());
    }
    
    return response;
}

@Override
public ApiResponse<CreditCardDTO> getCreditCardById(Long id) {
   
    ApiResponse<CreditCardDTO> response = new ApiResponse<>();

    try {
        CreditCard card = RepositoryAccessor.getCreditCardRepository().findById(id).orElse(null);

        CreditCardDTO cardDTO = CreditCardDTO.builder()
        .id(card.getId())
        .annualFee(card.getAnnualFee())
        .creditLimit(card.getAnnualFee())
        .description(card.getDescription())
        .name(card.getName())
        .interestRate(card.getInterestRate())
        .type(card.getType())
        .build();

        response.setCode(1);
        response.setMessage("successfully fetched Card");
        response.setData(cardDTO);
    } catch (Exception e) {
        response.setCode(1);
        response.setMessage("INTERNAL SERVER ERROR");
    }
    return response;
}

    

}
