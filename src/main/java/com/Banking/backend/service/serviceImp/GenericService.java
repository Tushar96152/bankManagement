package com.Banking.backend.service.serviceImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Banking.backend.Enums.AccountTypeName;
import com.Banking.backend.Enums.CardTypeName;
import com.Banking.backend.Enums.CreditCardType;
import com.Banking.backend.Enums.RoleName;
import com.Banking.backend.Enums.TransactionTypeName;
import com.Banking.backend.entity.AccountType;
import com.Banking.backend.entity.Branch;
import com.Banking.backend.entity.CardType;
import com.Banking.backend.entity.City;
import com.Banking.backend.entity.CreditCard;
import com.Banking.backend.entity.Role;
import com.Banking.backend.entity.TransactionType;
import com.Banking.backend.repository.RepositoryAccessor;

@Service
public class GenericService {


@Transactional
public void seedRoles() {
    List<RoleName> roleNames = Arrays.asList(RoleName.values());

    List<Role> existingRoles = RepositoryAccessor.getRoleRepository().findByIsActive(true);
    List<RoleName> existingRoleNames = existingRoles.stream()
            .map(Role::getName)
            .collect(Collectors.toList());

    List<Role> newRoles = roleNames.stream()
            .filter(name -> !existingRoleNames.contains(name)) // Only insert if not already in DB
            .map(name -> {
                Role role = new Role();
                role.setName(name);
                role.setActive(true);
                return role;
            })
            .collect(Collectors.toList());

    if (!newRoles.isEmpty()) {
        RepositoryAccessor.getRoleRepository().saveAll(newRoles);
    }
}
@Transactional
public void seedAccountTypes() {
    List<AccountTypeName> accountTypeNames = Arrays.asList(AccountTypeName.values());

    List<AccountType> existingTypes = RepositoryAccessor.getAccountTypeRepository().findByActive(true);
    List<AccountTypeName> existingTypeNames = existingTypes.stream()
            .map(AccountType::getName)
            .collect(Collectors.toList());

    List<AccountType> newTypes = accountTypeNames.stream()
            .filter(name -> !existingTypeNames.contains(name)) // Insert only if not already in DB
            .map(name -> {
                AccountType type = new AccountType();
                type.setName(name);
                type.setActive(true);
                return type;
            })
            .collect(Collectors.toList());

    if (!newTypes.isEmpty()) {
        RepositoryAccessor.getAccountTypeRepository().saveAll(newTypes);
    }
}

@Transactional
public void seedCardTypes() {
    List<CardTypeName> cardTypeNames = Arrays.asList(CardTypeName.values());

    List<CardType> existingTypes = RepositoryAccessor.getCardTypeRepository().findByActive(true);
    List<CardTypeName> existingTypeNames = existingTypes.stream()
            .map(CardType::getName)
            .collect(Collectors.toList());

    List<CardType> newTypes = cardTypeNames.stream()
            .filter(name -> !existingTypeNames.contains(name)) // Insert only if not already in DB
            .map(name -> {
                CardType type = new CardType();
                type.setName(name);
                type.setActive(true);
                return type;
            })
            .collect(Collectors.toList());

    if (!newTypes.isEmpty()) {
        RepositoryAccessor.getCardTypeRepository().saveAll(newTypes);
    }
}

@Transactional
public void seedTransactionTypes() {
    List<TransactionTypeName> transactionTypeNames = Arrays.asList(TransactionTypeName.values());

    List<TransactionType> existingTypes = RepositoryAccessor.getTransactionTypeRepository().findByActive(true);
    List<TransactionTypeName> existingTypeNames = existingTypes.stream()
            .map(TransactionType::getName)
            .collect(Collectors.toList());

    List<TransactionType> newTypes = transactionTypeNames.stream()
            .filter(name -> !existingTypeNames.contains(name)) // Insert only if not already in DB
            .map(name -> {
                TransactionType type = new TransactionType();
                type.setName(name);
                type.setActive(true);
                return type;
            })
            .collect(Collectors.toList());

    if (!newTypes.isEmpty()) {
        RepositoryAccessor.getTransactionTypeRepository().saveAll(newTypes);
    }
}

@Transactional
public void seedCreditCards() {

    if (RepositoryAccessor.getCreditCardRepository().count() > 0) {
        return; // Already seeded
    }
    
    List<CreditCard> cards = List.of(
        new CreditCard(null, CreditCardType.SILVER, "Silver Card", "Basic card with low annual fee.", 50.0, 12.5, 5000.0),
        new CreditCard(null, CreditCardType.GOLD, "Gold Card", "Premium card with added benefits.", 100.0, 14.0, 10000.0),
        new CreditCard(null, CreditCardType.PLATINUM, "Platinum Card", "Exclusive benefits with higher credit limits.", 150.0, 15.0, 20000.0),
        new CreditCard(null, CreditCardType.TITANIUM, "Titanium Card", "Card with luxury benefits and higher limits.", 200.0, 16.5, 25000.0),
        new CreditCard(null, CreditCardType.CLASSIC, "Classic Card", "Standard card with essential features.", 30.0, 13.0, 4000.0),
        new CreditCard(null, CreditCardType.VALUE_PLUS, "Value Plus Card", "Card designed for everyday purchases.", 75.0, 14.0, 6000.0),
        new CreditCard(null, CreditCardType.BLACK, "Black Card", "Exclusive luxury card for high-net-worth individuals.", 500.0, 18.0, 50000.0),
        new CreditCard(null, CreditCardType.INFINITE, "Infinite Card", "Prestige and luxury with limitless spending.", 1000.0, 19.5, 100000.0),
        new CreditCard(null, CreditCardType.SIGNATURE, "Signature Card", "Card with exceptional travel and dining privileges.", 250.0, 17.0, 30000.0),
        new CreditCard(null, CreditCardType.ROYALE, "Royale Card", "Exclusive perks with premier benefits.", 350.0, 16.0, 40000.0),
        new CreditCard(null, CreditCardType.IMPERIAL, "Imperial Card", "High-class card offering extensive luxury benefits.", 600.0, 18.5, 75000.0),
        new CreditCard(null, CreditCardType.PREMIER, "Premier Card", "Premium card with high rewards points and benefits.", 150.0, 15.5, 35000.0),
        new CreditCard(null, CreditCardType.TRAVEL, "Travel Card", "Card offering great travel perks and miles.", 100.0, 14.5, 20000.0),
        new CreditCard(null, CreditCardType.SHOPPING, "Shopping Card", "Card offering cashback and discounts on shopping.", 50.0, 12.5, 15000.0),
        new CreditCard(null, CreditCardType.CASHBACK, "Cashback Card", "Get cashback on all purchases with this card.", 90.0, 13.5, 18000.0),
        new CreditCard(null, CreditCardType.MOVIE, "Movie Card", "Exclusive discounts and offers for movie lovers.", 60.0, 12.0, 12000.0),
        new CreditCard(null, CreditCardType.DINING, "Dining Card", "Special offers and discounts at top restaurants.", 80.0, 13.0, 15000.0),
        new CreditCard(null, CreditCardType.ENTERTAINMENT, "Entertainment Card", "Enjoy exclusive access to concerts and events.", 120.0, 14.5, 22000.0),
        new CreditCard(null, CreditCardType.LIFESTYLE, "Lifestyle Card", "Premium card for the elite with lifestyle benefits.", 200.0, 15.0, 30000.0),
        new CreditCard(null, CreditCardType.BUSINESS_BASIC, "Business Basic Card", "Card for small businesses with basic features.", 60.0, 11.0, 10000.0),
        new CreditCard(null, CreditCardType.BUSINESS_PRO, "Business Pro Card", "Professional card with better rewards for businesses.", 100.0, 12.0, 20000.0),
        new CreditCard(null, CreditCardType.BUSINESS_ELITE, "Business Elite Card", "Exclusive benefits for large businesses.", 250.0, 15.0, 50000.0),
        new CreditCard(null, CreditCardType.CORPORATE_PLUS, "Corporate Plus Card", "Card for corporate executives with travel perks.", 300.0, 16.0, 60000.0),
        new CreditCard(null, CreditCardType.CORPORATE_EXECUTIVE, "Corporate Executive Card", "Card for top executives with elite benefits.", 350.0, 17.0, 75000.0),
        new CreditCard(null, CreditCardType.STUDENT, "Student Card", "A credit card designed for students with no annual fee.", 0.0, 10.5, 2000.0),
        new CreditCard(null, CreditCardType.YOUTH, "Youth Card", "Card for young adults with low fees and high rewards.", 25.0, 11.0, 3000.0),
        new CreditCard(null, CreditCardType.CAMPUS, "Campus Card", "A card for students with exclusive campus benefits.", 30.0, 12.0, 3500.0),
        new CreditCard(null, CreditCardType.GREEN, "Green Card", "Eco-friendly card offering sustainability perks.", 70.0, 13.0, 8000.0),
        new CreditCard(null, CreditCardType.CHARITY, "Charity Card", "Card supporting charitable causes with donation rewards.", 50.0, 12.0, 5000.0),
        new CreditCard(null, CreditCardType.WOMEN_ELITE, "Women Elite Card", "Card with special offers for women, including lifestyle and health benefits.", 150.0, 14.5, 20000.0)
    );

    RepositoryAccessor.getCreditCardRepository().saveAll(cards);

    System.out.println("✅ Credit Cards seeded successfully.");
}

@Transactional
public void seedBranches() {
    // Check if branches are already seeded
    if (RepositoryAccessor.getBranchRepository().count() > 0) {
        return; // Branches already seeded
    }

    // Manually defining the list of cities in Rajasthan
    List<City> citiesInRajasthan = List.of(
        new City(null, "Jaipur", "Rajasthan", "India", "302001"),
        new City(null, "Jodhpur", "Rajasthan", "India", "342001"),
        new City(null, "Udaipur", "Rajasthan", "India", "313001"),
        new City(null, "Kota", "Rajasthan", "India", "324001"),
        new City(null, "Ajmer", "Rajasthan", "India", "305001"),
        new City(null, "Bikaner", "Rajasthan", "India", "334001"),
        new City(null, "Alwar", "Rajasthan", "India", "301001"),
        new City(null, "Bhilwara", "Rajasthan", "India", "311001"),
        new City(null, "Bharatpur", "Rajasthan", "India", "321001"),
        new City(null, "Sikar", "Rajasthan", "India", "332001"),
        new City(null, "Chittorgarh", "Rajasthan", "India", "312001"),
        new City(null, "Pali", "Rajasthan", "India", "306401"),
        new City(null, "Tonk", "Rajasthan", "India", "304001"),
        new City(null, "Nagaur", "Rajasthan", "India", "341001"),
        new City(null, "Jhunjhunu", "Rajasthan", "India", "333001"),
        new City(null, "Hanumangarh", "Rajasthan", "India", "335512"),
        new City(null, "Barmer", "Rajasthan", "India", "344001"),
        new City(null, "Jaisalmer", "Rajasthan", "India", "345001"),
        new City(null, "Sawai Madhopur", "Rajasthan", "India", "322001"),
        new City(null, "Dholpur", "Rajasthan", "India", "328001")
    );

    // Save all the cities first
    RepositoryAccessor.getCityRepository().saveAll(citiesInRajasthan);

    // Create branches for each city
    List<Branch> branchesToSeed = citiesInRajasthan.stream()
        .map(city -> {
            // Generating branch name and a mock phone number based on city name
            String branchName = city.getName() + " Branch";
            String phoneNumber = "123" + (city.getName().hashCode() % 1000000000); // Unique phone numbers
            return new Branch(null, branchName, phoneNumber, city);
        })
        .toList();

    // Save all the generated branches
    if (!branchesToSeed.isEmpty()) {
        RepositoryAccessor.getBranchRepository().saveAll(branchesToSeed);
        System.out.println("✅ Branches for all cities in Rajasthan seeded successfully.");
    } else {
        System.out.println("ℹ️ No branches to seed.");
    }
}



}
