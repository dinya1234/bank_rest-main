package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));
    }

    public Card createCard(Card card, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        card.setUser(user);
        return cardRepository.save(card);
    }

    public List<Card> getCardsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return cardRepository.findByUser(user);
    }
}