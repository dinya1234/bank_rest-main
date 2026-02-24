package com.example.bankcards;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class BaseTest {

    @BeforeEach
    void setUpBase() {
        MockitoAnnotations.openMocks(this);
    }
}