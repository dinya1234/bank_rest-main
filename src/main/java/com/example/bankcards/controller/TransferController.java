package com.example.bankcards.controller;

import com.example.bankcards.dto.TransferRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.repository.CardRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final CardRepository cardRepository;

    public TransferController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {

        Card fromCard = cardRepository.findById(request.getFromCardId())
                .orElseThrow(() -> new RuntimeException("Карта отправителя не найдена"));
        Card toCard = cardRepository.findById(request.getToCardId())
                .orElseThrow(() -> new RuntimeException("Карта получателя не найдена"));

        if (fromCard.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException("Недостаточно средств");
        }

        if (fromCard.getStatus() != Card.CardStatus.ACTIVE) {
            return ResponseEntity.badRequest().body("Карта отправителя не активна");
        }
        if (toCard.getStatus() != Card.CardStatus.ACTIVE) {
            return ResponseEntity.badRequest().body("Карта получателя не активна");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(request.getAmount()));
        toCard.setBalance(toCard.getBalance().add(request.getAmount()));

        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        return ResponseEntity.ok("Перевод выполнен");
    }
}