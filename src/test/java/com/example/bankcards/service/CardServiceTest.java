package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardService cardService;

    private User user;
    private Card card;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setRole("USER");

        card = new Card();
        card.setId(1L);
        card.setCardNumber("1234567890123456");
        card.setOwnerName("TEST USER");
        card.setExpiryDate("12/25");
        card.setStatus(Card.CardStatus.ACTIVE);
        card.setBalance(BigDecimal.valueOf(1000));
        card.setUser(user);
    }

    @Test
    void getCardById_Success() {
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        Card found = cardService.getCardById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals("1234567890123456", found.getCardNumber());
    }

    @Test
    void getCardById_NotFound() {
        when(cardRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            cardService.getCardById(99L);
        });
    }

    @Test
    void getCardsByUsername_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Card> cardPage = new PageImpl<>(List.of(card));

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(cardRepository.findByUser(user, pageable)).thenReturn(cardPage);

        Page<Card> result = cardService.getCardsByUsername("testuser", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }


    @Test
    void createCard_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card created = cardService.createCard(card, "testuser");

        assertNotNull(created);
        assertEquals("1234567890123456", created.getCardNumber());
        assertEquals(user, created.getUser());
    }


    @Test
    void getAllCards_WithPagination() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Card> cardPage = new PageImpl<>(List.of(card));

        when(cardRepository.findAll(pageable)).thenReturn(cardPage);

        Page<Card> result = cardService.getAllCards(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}