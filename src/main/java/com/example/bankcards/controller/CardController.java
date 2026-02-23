package com.example.bankcards.controller;

import com.example.bankcards.entity.Card;
import com.example.bankcards.service.CardService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/{id}")
    public Card getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    @PostMapping
    public Card createCard(@RequestBody Card card, @RequestParam Long userId) {
        return cardService.createCard(card, userId);
    }

    @GetMapping("/user/{userId}")
    public List<Card> getCardsByUser(@PathVariable Long userId) {
        return cardService.getCardsByUser(userId);
    }
}