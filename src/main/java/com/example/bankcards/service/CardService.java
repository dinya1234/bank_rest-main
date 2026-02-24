package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    public Page<Card> getAllCards(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Карта не найдена с id: " + id));
    }

    public Card createCard(Card card, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        card.setUser(user);
        return cardRepository.save(card);
    }

    public Page<Card> getCardsByUsername(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return cardRepository.findByUser(user, pageable);
    }

    public Page<Card> searchCards(String ownerName, Card.CardStatus status, Pageable pageable) {
        if (ownerName != null && status != null) {
            return cardRepository.findByOwnerNameContainingIgnoreCaseAndStatus(ownerName, status, pageable);
        } else if (ownerName != null) {
            return cardRepository.findByOwnerNameContainingIgnoreCase(ownerName, pageable);
        } else if (status != null) {
            return cardRepository.findByStatus(status, pageable);
        } else {
            return cardRepository.findAll(pageable);
        }
    }
}