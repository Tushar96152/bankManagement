package com.Banking.backend.service.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Banking.backend.dto.request.CreateAccountRequest;
import com.Banking.backend.dto.response.ApiResponse;
import com.Banking.backend.dto.response.CreateAccountResponse;
import com.Banking.backend.entity.AccountType;
import com.Banking.backend.entity.BankAccount;
import com.Banking.backend.entity.Branch;
import com.Banking.backend.entity.Card;
import com.Banking.backend.entity.CardType;
import com.Banking.backend.entity.User;
import com.Banking.backend.repository.RepositoryAccessor;
import com.Banking.backend.service.BankAccountService;
import com.Banking.backend.utils.AccountNumberGenerator;
import com.Banking.backend.utils.DebitCardGenerator;
import com.Banking.backend.utils.UserNetIdGenerator;

@Service
public class BankAccountServiceImpl implements BankAccountService {

@Override
public ApiResponse<CreateAccountResponse> createBankAccount(CreateAccountRequest request) {
    ApiResponse<CreateAccountResponse> response = new ApiResponse<>();

    try {
        
        User user = RepositoryAccessor.getUserRepository()
                        .findById(request.getUserId())
                        .orElse(null);

        System.out.println(request.getUserId());;

        if (user == null) {
            response.setMessage("User not found with ID: " + request.getUserId());
            response.setCode(0);
            return response;
        }

       
        Branch branch = RepositoryAccessor.getBranchRepository()
                            .findById(request.getBranchId())
                            .orElse(null);

        if (branch == null) {
            response.setMessage("Branch not found with ID: " + request.getBranchId());
            response.setCode(0);
            return response;
        }

       
        AccountType type = RepositoryAccessor.getAccountTypeRepository()
                            .findById(request.getAccountTypeId())
                            .orElse(null);

        if (type == null) {
            response.setMessage("Account type not found with ID: " + request.getAccountTypeId());
            response.setCode(0);
            return response;
        }

        BankAccount account = new BankAccount();
        account.setUser(user);
        account.setBranch(branch);
        account.setType(type);
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setBalance(request.getBalance());
        account.setNomineeName(request.getNomineeName());
        account.setNomineeRelation(request.getNomineeRelation());
        account.setAadhaarNumber(request.getAadhaarNumber());
        account.setDocumentType(request.getDocumentType());
        account.setDocumentNumber(request.getDocumentNumber());
        account.setNetBankingEnabled(request.isNetBankingEnabled());
        account.setDebitCardRequired(request.isDebitCardRequired());

        
        if (Boolean.TRUE.equals(request.isNetBankingEnabled())) {
            account.setUserNetId(generateUniqueUserNetId());
        } else {
            account.setUserNetId(null); 
        }


BankAccount savedAccount = RepositoryAccessor.getBankAccountRepository().save(account);

List<Card> savedCard = new ArrayList<>();
if (Boolean.TRUE.equals(request.isDebitCardRequired())) {
    Card card = generateDebitCard(savedAccount.getAccountNumber());
    card.setAccount(savedAccount); 
    savedCard.add(RepositoryAccessor.getCardRepository().save(card));
}


savedAccount.setCards(savedCard);


// Prepare response
CreateAccountResponse createAccountResponse = CreateAccountResponse.builder()
        .accountNumber(savedAccount.getAccountNumber())
        .id(savedAccount.getId())
        .branchName(savedAccount.getBranch().getName())
        .userName(savedAccount.getUser().getName())
        .accountTypeName(savedAccount.getType().getName().name())
        .isActive(savedAccount.isActive())
        .cardNumbers(savedAccount.getCards().get(0).getCardNumber())
        .build();

            response.setData(createAccountResponse);
            response.setCode(1);
            response.setMessage("Bank account created successfully");


    } catch (Exception e) {
        response.setCode(0);
        response.setMessage("Error while creating account: " + e.getMessage());
        e.printStackTrace(); 
    }

    return response;
}


public String generateUniqueAccountNumber() {
    String accountNumber;
    do {
        // Generate the account number (replace this with your actual logic)
        accountNumber = AccountNumberGenerator.generate();
        
        // Log to check if '0' is generated
        if ("0".equals(accountNumber)) {
            System.out.println("Generated account number is 0, regenerating...");
        }
    } while (RepositoryAccessor.getBankAccountRepository().existsByAccountNumber(accountNumber) || "0".equals(accountNumber));
    return accountNumber;
}



    @Override
    public String generateUniqueUserNetId() {
        String userNetId;
        do {
            // Generate a new userNetId
            userNetId = UserNetIdGenerator.generateUserNetId();
        } while (userNetId == null || RepositoryAccessor.getBankAccountRepository().existsByUserNetId(userNetId));
        return userNetId;
    }
    

    @Override
    public Card generateDebitCard(String accountNumber) {

        BankAccount account = RepositoryAccessor.getBankAccountRepository().findByAccountNumber(accountNumber).orElse(null);
        CardType cardType = RepositoryAccessor.getCardTypeRepository().findById(1l).orElse(null);
        Card  debitCard  = new Card();
 String cardNumber;
    do {
        cardNumber = DebitCardGenerator.generateCardNumber();
    } while (RepositoryAccessor.getCardRepository().existsByCardNumber(cardNumber));

    debitCard.setCardNumber(cardNumber);
    debitCard.setCvv(DebitCardGenerator.generateCVV());
    debitCard.setExpiryDate(DebitCardGenerator.generateExpiryDate());
    debitCard.setPin("0000");
    debitCard.setAccount(account);
    debitCard.setType(cardType);
    debitCard.setStatus(true);

    System.out.println(debitCard.getCardNumber());
    return debitCard;
    }
    

}
