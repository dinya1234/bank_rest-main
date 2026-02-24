package com.example.bankcards.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Converter
@Component
public class CardNumberConverter implements AttributeConverter<String, String> {

    private static EncryptionUtil encryptionUtil;

    @Autowired
    public void setEncryptionUtil(EncryptionUtil encryptionUtil) {
        CardNumberConverter.encryptionUtil = encryptionUtil;
    }

    @Override
    public String convertToDatabaseColumn(String cardNumber) {
        if (cardNumber == null) return null;
        return encryptionUtil.encrypt(cardNumber);
    }

    @Override
    public String convertToEntityAttribute(String encryptedData) {
        if (encryptedData == null) return null;
        return encryptionUtil.decrypt(encryptedData);
    }
}