package com.example.bankcards.controller;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public AdminController(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/cards")
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/cards/{id}/block")
    public Card blockCard(@PathVariable Long id) {
        Card card = cardRepository.findById(id).orElseThrow();
        card.setStatus(Card.CardStatus.BLOCKED);
        return cardRepository.save(card);
    }

    @PutMapping("/cards/{id}/activate")
    public Card activateCard(@PathVariable Long id) {
        Card card = cardRepository.findById(id).orElseThrow();
        card.setStatus(Card.CardStatus.ACTIVE);
        return cardRepository.save(card);
    }
}