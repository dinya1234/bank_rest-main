package com.example.bankcards;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import java.math.BigDecimal;

public class TestDataFactory {

    public static User createUser(String username, String role) {
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("encodedPassword");
        user.setRole(role);
        return user;
    }

    public static Card createCard(User user, String number, BigDecimal balance) {
        Card card = new Card();
        card.setId(1L);
        card.setCardNumber(number);
        card.setOwnerName("TEST USER");
        card.setExpiryDate("12/25");
        card.setStatus(Card.CardStatus.ACTIVE);
        card.setBalance(balance);
        card.setUser(user);
        return card;
    }
}