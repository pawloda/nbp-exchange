package com.exchange_app.exchanges;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
interface ExchangeService {
    @Transactional
    void exchange(UUID id, Currency currencyToExchange, BigDecimal sum) throws JsonProcessingException;
}
